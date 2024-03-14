package vn.funix.FX18760.java.asm04.unittest;

import org.junit.jupiter.api.Test;
import vn.funix.FX18760.java.asm04.models.Account;
import vn.funix.FX18760.java.asm04.models.Customer;
import vn.funix.FX18760.java.asm04.models.DigitalBank;
import vn.funix.FX18760.java.asm04.models.SavingsAccount;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DigitalBankTest {
    @Test
    public void testIsCustomerExisted_ExistingCustomer() {
        // Tạo danh sách khách hàng và khách hàng cần kiểm tra
        List<Customer> customersFileDat = new ArrayList<>();
        customersFileDat.add(new Customer("064091012001", "Nguyen Van A"));
        customersFileDat.add(new Customer("064091012002", "Nguyen Van B"));
        Customer customer = new Customer("064091012001", "Nguyen Van A");

        // Kiểm tra phương thức isCustomerExisted
        assertTrue(DigitalBank.isCustomerExisted(customersFileDat, customer));
    }

    @Test
    public void testIsCustomerExisted_NonExistingCustomer() {
        // Tạo danh sách khách hàng và khách hàng cần kiểm tra
        List<Customer> customersFileDat = new ArrayList<>();
        customersFileDat.add(new Customer("064091012001", "Nguyen Van A"));
        customersFileDat.add(new Customer("064091012002", "Nguyen Van B"));
        Customer customer = new Customer("064091012003", "Nguyen Van C");

        // Kiểm tra phương thức isCustomerExisted
        assertFalse(DigitalBank.isCustomerExisted(customersFileDat, customer));
    }

    @Test
    public void testIsAccountExisted_ExistingAccount() {
        // Tạo danh sách tài khoản và số tài khoản cần kiểm tra
        List<Account> accountsList = new ArrayList<>();
        accountsList.add(new SavingsAccount("123456", 1000000,"064091012014"));
        accountsList.add(new SavingsAccount("789012", 2000000, "064091012015"));
        String accountNumber = "123456";

        // Kiểm tra phương thức isAccountExisted
        assertTrue(DigitalBank.isAccountExisted(accountsList, accountNumber));
    }

    @Test
    public void testIsAccountExisted_NonExistingAccount() {
        // Tạo danh sách tài khoản và số tài khoản cần kiểm tra
        List<Account> accountsList = new ArrayList<>();
        accountsList.add(new SavingsAccount("123456", 1000000,"064091012014"));
        accountsList.add(new SavingsAccount("789012", 2000000, "064091012015"));
        String accountNumber = "345678";

        // Kiểm tra phương thức isAccountExisted
        assertFalse(DigitalBank.isAccountExisted(accountsList, accountNumber));
    }
}