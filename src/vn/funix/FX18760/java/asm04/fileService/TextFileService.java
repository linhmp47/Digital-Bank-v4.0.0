package vn.funix.FX18760.java.asm04.fileService;

import vn.funix.FX18760.java.asm04.models.Customer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static vn.funix.FX18760.java.asm04.Asm04.isValidId;
public class TextFileService {
    private static final String COMMA_DELIMITER = ",";
    public static <T> List<T> readFile(String fileName) throws IOException{
        List<T> customerListTxt = new ArrayList<T>();
        AtomicInteger lineNum = new AtomicInteger();

        List<String> lines = Files.readAllLines(Paths.get(fileName));
        lines.forEach(line ->{
            lineNum.getAndIncrement();
            if (!line.isEmpty()){
                if(!line.contains(COMMA_DELIMITER)) {
                    System.out.println("Dòng " + lineNum + " sai định dạng");
                } else {
                    List<String> lineCustomer = new ArrayList<>(Arrays.asList(line.split(COMMA_DELIMITER)));
                    //valid
                    if(isValidId(lineCustomer.get(0))){
                        Customer customer = new Customer(lineCustomer.get(1), lineCustomer.get(0));
                        customerListTxt.add((T) customer);
                    } else {
                        System.out.println("Khách hàng " + lineCustomer.get(0)+" không đúng định dạng CCCD");
                    }
                }

            } else {
                System.out.println("Dòng số "+ lineNum + " chưa điền thông tin khách hàng");
            }

        });
        return customerListTxt;
    }

}
