package vn.funix.FX18760.java;

import vn.funix.FX18760.java.asm04.exception.EmptyException;
import vn.funix.FX18760.java.asm04.exception.PatternException;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

import static vn.funix.FX18760.java.asm04.Asm04.menuChoices;


/**
 * chứa các chức năng chung được dùng trên toàn bộ project
 */

public  class Utils {
    private static final Scanner sc = new Scanner(System.in);

    public static final double SAVING_ACCOUNT_MAX_WITHDRAW = 5000000;
    public static final double MIN_WITHDRAW = 50000;
    public static final String PATTERN_NUMBER_CUSTOMER_ID = "[0-9]{12}";
    public static final String PATTERN_ACCOUNT_NUMBER = "[0-9]{6}";
    public static final String PATTERN_YES_NO = "[Y,y,N,n]";

    public static final int MIN_PREMIUM_BALANCE = 10000000;
    public static final int MIN_BALANCE = 50000;

    public static String getTitle(){
        return "BIEN LAI GIAO DICH";
    }

    public static String getDivider(){
        return "+-----------------------------------------------------+" ;
    }

    public static String getDateTime(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        return dtf.format(LocalDateTime.now());
    }

    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#,###");

    public static String format(double number) {
        return DECIMAL_FORMAT.format(number) + " đ";
    }

    public static String format() {
        return DECIMAL_FORMAT.format(0) + " đ";
    }

    public static <T> boolean checkNullPointerLst(List<T> tList){
        return tList == null || tList.isEmpty();
    }

    /**
     * Nhập vào kiểu dữ liệu chuỗi và kiểm tra thông tin có hợp lệ không?
     * @return chuỗi dữ liệu đã nhập
     */
    public static String getString(){
        String input = "";
        boolean isContinue = true;
        while (isContinue) {
            try {
                input = sc.nextLine();
                if (Objects.equals(input, "exit")) {
                    menuChoices();
                }
                if (checkNullString(input))
                    throw new EmptyException("Thông tin nhập được để trống. Vui lòng nhập lại hoặc nhập 'exit' để thoát!");
                if (exitApplication(input)) break;
                isContinue = false;
            } catch (EmptyException e){
                System.out.println(e.getMessage());
            }
        }
        return input;
    }

    public static boolean checkNullString(String input) {
        return input == null || input.trim().isEmpty();
    }

    public static boolean exitApplication(String input) {
        return input.equalsIgnoreCase("no");
    }

    /**
     * Lấy thông tin cho số tài khoản ngân hàng
     * @return số tài khoản ngân hàng đã nhận
     */
    public static String getAccountNumber(){
        String input = "";
        boolean isContinue = true;
        while (isContinue) {
            try {
                input = sc.nextLine();
                if (Objects.equals(input, "exit")) {
                    menuChoices();
                }
                if(checkNullString(input))
                    throw new EmptyException("Thông tin nhập được để trống. Vui lòng nhập lại hoặc nhập 'exit' ");
                if (exitApplication(input)) break;
                if (!input.matches(PATTERN_ACCOUNT_NUMBER))
                    throw new PatternException("Số tài khoản phải gồm 6 chữ số: ");
                isContinue = false;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return input;
    }

    public static String getYesNo(){
        String input = "";
        boolean isContinue = true;
        while (isContinue) {
            try {
                input = sc.nextLine();
                if (Objects.equals(input, "exit")) {
                    menuChoices();
                }
                if(checkNullString(input))
                    throw new EmptyException("Thông tin nhập được để trống. Vui lòng nhập lại hoặc nhập 'exit' để thoát: ");
                if (exitApplication(input)) break;
                if (!input.matches(PATTERN_YES_NO))
                    throw new PatternException("Vui lòng nhập lại (Y/N) hoặc nhập 'exit' để thoát: ");
                isContinue = false;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return input;
    }


    public static int getToInteger(){
        int number = 0;
        boolean isContinue = true;
        while (isContinue) {
            try {
                String input = sc.nextLine();
                if (Objects.equals(input, "exit")) {
                    menuChoices();
                }
                if(checkNullString(input))
                    throw new EmptyException("Thông tin nhập được để trống. Vui lòng nhập lại hoặc nhập 'exit' để thoát: ");
                if (exitApplication(input)) break;
                try {
                    number = Integer.parseInt(input);
                    isContinue = false;
                } catch (NumberFormatException e) {
                    System.out.println("Số bạn nhập không hợp lệ. Vui lòng nhập lại hoặc nhập 'exit' để thoát:  ");
                }
            } catch (EmptyException e) {
                System.out.println(e.getMessage());
            }
        }
        return number;
    }

    public static double getToDouble(){
        double number = 0;
        boolean isContinue = true;
        while (isContinue) {
            try {
                String input = sc.nextLine();
                if (Objects.equals(input, "exit")) {
                    menuChoices();
                }
                if(checkNullString(input))
                    throw new EmptyException("Thông tin nhập được để trống. Vui lòng nhập lại hoặc nhập 'exit' để thoát: ");
                if (exitApplication(input)) break;
                try {
                    number = Double.parseDouble(input);
                    isContinue = false;
                } catch (NumberFormatException e) {
                    System.out.println("Số bạn nhập không hợp lệ. Vui lòng nhập lại hoặc nhập 'exit' để thoát:  ");
                }
            } catch (EmptyException e) {
                System.out.println(e.getMessage());
            }
        }
        return number;
    }

}
