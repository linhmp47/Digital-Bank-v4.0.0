package vn.funix.FX18760.java.asm04;

import vn.funix.FX18760.java.Utils;
import vn.funix.FX18760.java.asm04.enums.MenuType;
import vn.funix.FX18760.java.asm04.exception.NumberNotFoundException;
import vn.funix.FX18760.java.asm04.models.Customer;
import vn.funix.FX18760.java.asm04.models.DigitalBank;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.*;

public class Asm04 {
    private static final String AUTHOR = "FX18760";
    private static final String VERSION = "v4.0.0";
    private static final Scanner scanner = new Scanner(System.in);
    private static final DigitalBank digitalBank = new DigitalBank();// Thực hiện theo bank
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        menuChoices();
    }

    private static void menu() {
        System.out.println("+-----------------------------------------------------+");
        System.out.println("|         NGÂN HÀNG SỐ | " + AUTHOR + "@" + VERSION + "               |");
        System.out.println("+-----------------------------------------------------+");
        System.out.printf("|%-53s|\n", "1. Xem danh sách khách hàng");
        System.out.printf("|%-53s|\n", "2. Nhập danh sách khách hàng");
        System.out.printf("|%-53s|\n", "3. Thêm tài khoản ATM");
        System.out.printf("|%-53s|\n", "4. Chuyển tiền");
        System.out.printf("|%-53s|\n", "5. Rút tiền");
        System.out.printf("|%-53s|\n", "6. Tra cứu lịch sử giao dịch");
        System.out.printf("|%-53s|\n", "0.Thoát");
        System.out.println("+-----------------------------------------------------+");
        System.out.println("Chức năng cần chọn: ");
    }


    /**
     * Menu chương trình
     */

    public static void menuChoices() {
        do {
            menu();
            int choice = getNumberMenu();
            MenuType menuType = MenuType.getMenuType(choice);
            switch (menuType) {
                case LIST_CUSTOMER:
                    customerList();
                    break;
                case ADD_CUSTOMER:
                    createCustomer();
                    break;
                case ADD_ACCOUNT_ATM:
                    createAccountATM();
                    break;
                case TRANSFERS:
                    transferMoney();
                    break;
                case WITHDRAW:
                    withdrawMoney();
                    break;
                case TRANSACTION_HISTORY:
                    displayHistoryTransaction();
                    break;
                case EXIT:
                    System.exit(0);
                    break;
                default:
                    break;
            }
        } while (true);
    }

    /**
     * Nhập số giá trị menu
     *
     * @return số chức năng từ 0->6
     */
    private static int getNumberMenu() {
        int choice = 0;
        boolean isContinue = true;
        do {
            try {
                choice = Utils.getToInteger();
                if (choice < 0 || choice > 7) {
                    throw new NumberNotFoundException("Số bạn nhận không thỏa mã từ 0 đến 6. Vui lòng thử lại....");
                }
                isContinue = false;
            } catch (NumberFormatException e) {
                System.out.println(e.getMessage());
            } catch (NumberNotFoundException e) {
                throw new RuntimeException(e);
            }
        } while (isContinue);
        return choice;
    }

    /**
     * Hiển thị lịch sử giao dịch
     */
    public static void displayHistoryTransaction() {
        System.out.println("Nhập mã số của khách hàng: ");
        String customerId = Utils.getString();
        Customer.displayTransactionInformation(customerId);
    }

    /**
     * Danh sách khách hàng
     */
    private static void customerList(){
        digitalBank.showCustomers();
    }

    /**
     * Chức năng nhập danh sách khách hàng
     */
    private static void createCustomer() {
        System.out.println(Utils.getDivider());
        System.out.println("Nhập đường dẫn đến tệp: ");
        String filePath = Utils.getString();
        try {
            if(checkFileExtension(filePath)) {
                digitalBank.addCustomers(filePath);
            }
        } catch (FileNotFoundException e) {

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Chức năng thêm tài khoản ATM
     */
    public static void createAccountATM() {
        System.out.println("Nhập mã số của khách hàng: ");
        String customerId = Utils.getString();
        digitalBank.addSavingAccount(scanner, customerId);
    }

    /**
     * Chức năng chuyển tiền
     */
    private static void transferMoney() {
        System.out.println("Nhập mã số khách hàng: ");
        String customerId = Utils.getString();
        digitalBank.transfers(scanner, customerId);
    }

    /**
     * Chức năng rút tiền
     */
    private static void withdrawMoney() {
        System.out.println("Nhập mã số khách hàng: ");
        String customerId = Utils.getString();
        digitalBank.withdraw(scanner, customerId);
    }

    /**
     * Hàm kiểm tra phần mở rộng của tệp
     *
     * @param filePath          Đường dẫn tệp
     * @return True nếu phần mở rộng của tệp khớp với phần mở rộng dự kiến, ngược lại trả về false
     */
    private static boolean checkFileExtension(String filePath) {
        try {
            //check tệp có tồn tại hay không
            List<String> lines = Files.readAllLines(Paths.get(filePath));

            //check tệp rỗng hay ko
            if (lines.isEmpty()) {
                System.out.println("Tệp rỗng, hãy nhập dữ liệu khách hàng để thêm vào danh sách");
                return false;
            }

        } catch (NoSuchFileException noSuchFile) {
            System.out.println("Tệp không tồn tại ");
            menuChoices();
        } catch (InvalidPathException | IOException invalidPath) {
            System.out.println("Đây không phải là định dạng của một dường dẫn!");
        }
        return true;
    }

    public static boolean isValidId(String customerId) {

        try {
            String firstCharOfCustomerId = String.valueOf(customerId.charAt(0));
            int firstCharOfCustomerIdToInt = Integer.parseInt(firstCharOfCustomerId);

            for (int i = 1; i < customerId.length(); i++) {
                String elevenEndCharOfCustomerId = "";
                elevenEndCharOfCustomerId += customerId.charAt(i);
                int elevenEndCharOfCustomerIdToInt = Integer.parseInt(elevenEndCharOfCustomerId);
            }

            if(customerId.length() != 12) {
                throw new Exception("số CCCD không đúng 12 chữ số, vui lòng nhập lại");
            }

            if(!CheckAreaCode(customerId)) {
                throw new Exception("số CCCD không thuộc mã vùng, vui lòng nhập lại");
            }
            return true;
        }
        catch (Exception e) {
            return false;
        }

    }

    static boolean CheckAreaCode(String customerId) {
        // check 3 ki tu dau co nam trong danh sach cac tinh thanh hay ko
        // chi lay khi so CCCD nhap vao co chieu dai lon hon hoac bang 4
        if (customerId.length() >=4) {
            String areaCodeOfCustomerId = customerId.substring(0, 3);
            for (int i = 0; i < 63; i++) {
                if (Tinh()[i][0].equals(areaCodeOfCustomerId)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static String[][] Tinh() {
        String[][] ns = {{"001", "Hà Nội"}, {"002", "Hà Giang"}, {"004", "Cao Bằng"}, {"006", "Bắc Kạn"}, {"008", "Tuyên Quang"}, {"010", "Lào Cai"}, {"011", "Điện Biên"}, {"012", "Lai Châu"}, {"014", "Sơn La"}, {"015", "Yên Bái"}, {"017", "Hòa Bình"}, {"019", "Thái Nguyên"}, {"020", "Lạng Sơn"}, {"022", "Quảng Ninh"}, {"024", "Bắc Giang"}, {"025", "Phú Thọ"}, {"026", "Vĩnh Phúc"}, {"027", "Bắc Ninh"}, {"030", "Hải Dương"}, {"031", "Hải Phòng"}, {"033", "Hưng Yên"}, {"034", "Thái Bình"}, {"035", "Hà Nam"}, {"036", "Nam Định"}, {"037", "Ninh Bình"}, {"038", "Thanh Hóa"}, {"040", "Nghệ An"}, {"042", "Hà Tĩnh"}, {"044", "Quảng Bình"}, {"045", "Quảng Trị"}, {"046", "Thừa Thiên Huế"}, {"048", "Đà Nẵng"}, {"049", "Quảng Nam"}, {"051", "Quảng Ngãi"}, {"052", "Bình Định"}, {"054", "Phú Yên"}, {"056", "Khánh Hòa"}, {"058", "Ninh Thuận"}, {"060", "Bình Thuận"}, {"062", "Kon Tum"}, {"064", "Gia Lai"}, {"066", "Đắk Lắk"}, {"067", "Đắk Nông"}, {"068", "Lâm Đồng"}, {"070", "Bình Phước"}, {"072", "Tây Ninh"}, {"074", "Bình Dương"}, {"075", "Đồng Nai"}, {"077", "Bà Rịa - Vũng Tàu"}, {"079", "Hồ Chí Minh"}, {"080", "Long An"}, {"082", "Tiền Giang"}, {"083", "Bến Tre"}, {"084", "Trà Vinh"}, {"086", "Vĩnh Long"}, {"087", "Đồng Tháp"}, {"089", "An Giang"}, {"091", "Kiên Giang"}, {"092", "Cần Thơ"}, {"093", "Hậu Giang"}, {"094", "Sóc Trăng"}, {"095", "Bạc Liêu"}, {"096", "Cà Mau"}};
        return ns;
    }

}
