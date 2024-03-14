package vn.funix.FX18760.java.asm04.fileService;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Định nghĩa lớp có chức năng cung cấp các dịch vụ đọc/ghi file nhị phân
 */

public class BinaryFileService {
    /**
     * Đọc file nhị phân
     * @param fileName tên file
     * @return danh sách đã đọc được từ file nếu có lỗi trả về Exception
     * @param <T> chứa 1 lớp object nào đó
     */
    public static <T> List<T> readFile(String fileName) {
        List<T> objects = new ArrayList<>();
        try(ObjectInputStream file = new ObjectInputStream(new BufferedInputStream(new FileInputStream(fileName)))){
            boolean eof =false;
            while (!eof){
                try {
                    T object = (T) file.readObject();
                    objects.add(object);
                }catch (EOFException e){
                    eof = true;
                }
            }

        }catch (EOFException e){
            return new ArrayList<>();
        }catch (IOException io){
            System.out.println("IO Exception"+io.getMessage());
        }catch (ClassNotFoundException c){
            System.out.println("ClassNotFoundException: "+c.getMessage());
        }

        return objects;
    }

    /**
     * Ghi file nhị phân
     * @param fileName tên file
     * @param objects danh sách cần đọc
     * @param <T> đối tượng theo danh sách
     */
    public static <T> void writeFile(String fileName, List<T> objects){
        try(ObjectOutputStream objectOutputStream = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(fileName,false)))){
            for (T object: objects){
                objectOutputStream.writeObject(object);
            }
        }catch (IOException io){
            System.out.println("IO Exception: " + io.getMessage());
        }
    }

}
