package vn.funix.FX18760.java.asm04.unittest;

import org.junit.Test;
import vn.funix.FX18760.java.asm04.models.Account;
import vn.funix.FX18760.java.asm04.models.SavingsAccount;
import vn.funix.FX18760.java.asm04.models.Customer;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

public class CustomerTest {
    @Test
    public void testIsPremiumAccount_PremiumAccount() {
        // Tạo danh sách tài khoản với tài khoản normal
        List<Account> accounts = new ArrayList<>();
        accounts.add(new SavingsAccount("123456", 100000, "064091012001"));
        accounts.add(new SavingsAccount("789012", 200000, "064091012001"));

        // Kiểm tra phương thức isPremiumAccount với tài khoản normal
        assertFalse(new Customer("064091012001", "Nguyen Van A", accounts).isPremiumAccount());
    }

    @Test
    public void testIsPremiumAccount_NormalAccount() {
        // Tạo danh sách tài khoản với tài khoản normal
        List<Account> accounts = new ArrayList<>();
        accounts.add(new SavingsAccount("123456", 100000, "064091012001"));
        accounts.add(new SavingsAccount("789012", 200000, "064091012001"));

        // Kiểm tra phương thức isPremiumAccount với tài khoản normal
        assertFalse(new Customer("064091012001", "Nguyen Van A", accounts).isPremiumAccount());
    }
}
