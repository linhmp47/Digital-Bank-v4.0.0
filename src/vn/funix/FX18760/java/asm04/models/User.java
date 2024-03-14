package vn.funix.FX18760.java.asm04.models;
import java.io.Serializable;

/**
 * Thông tin cá nhân người dùng
 */
public class User implements Serializable {
    @java.io.Serial
    private static final long serialVersionUID = 9021126834628965561L;
    private String name;
    private String customerId;

    public User(String name, String customerId) {
        this.name = name;
        this.customerId = customerId;
    }

    public String getName() {
        return name;
    }
    public String getCustomerId() {
        return customerId;
    }

    public static boolean isValidId(String customerId) {
        int length= customerId.length();
        for (int i = 0; i <length; i++) {
            int a = Integer.parseInt(String.valueOf(customerId.charAt(i)));
            if (a < 0 || a > 9 || length!=12) {
                return false;
            }
        }
        return true;
    }

}
