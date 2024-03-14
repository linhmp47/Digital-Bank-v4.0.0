package vn.funix.FX18760.java.asm04.models;

import vn.funix.FX18760.java.Utils;
import vn.funix.FX18760.java.asm04.dao.AccountDao;
import vn.funix.FX18760.java.asm04.dao.CustomerDao;
import vn.funix.FX18760.java.asm04.dao.TransactionDao;
import vn.funix.FX18760.java.asm04.enums.AccountType;
import vn.funix.FX18760.java.asm04.service.ICustomer;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static vn.funix.FX18760.java.Utils.MIN_BALANCE;
import static vn.funix.FX18760.java.asm04.models.DigitalBank.*;

/**
 * Quản lí thông tin khách hàng
 */

public class Customer extends User implements Serializable, ICustomer {
    List<Account> accounts;
    @Serial
    private static final long serialVersionUID = -4697893504351612252L;

    public Customer(String customerId, String name){
        super(customerId,name);
    }

    public Customer(String customerId, String name, List<Account> accounts ){
        super(customerId,name);
        this.accounts = getAccounts();
    }

    public List<Customer> getList(){
        return CustomerDao.list();
    }
    public void save(List<Customer> customers){
        CustomerDao.save(customers);
    }

    public Customer(List<String> values) {
        this(values.get(0), values.get(1));
    }

    /**
     * Danh sách tài khoản của hệ thống
     * @return
     */
    public List<SavingsAccount> getListAccounts(){
        return AccountDao.list();
    }

    /**
     * Danh sách tài khoản của khách hàng theo căn cước công dân
     * @return danh sách tài khoản của khách hàng
     */
    public List<Account> getAccounts() {
        return this.getListAccounts().stream()
                .filter(account -> this.getCustomerId().equals(account.getCustomerId()))
                .collect(Collectors.toList());
    }

    public List<Account> getAccountsByCustomerId() {
        return this.getListAccounts().stream()
                .filter(account -> this.getCustomerId().equals(account.getCustomerId()))
                .collect(Collectors.toList());
    }

    /**
     * Hiển thị thông tin khách hàng
     */
    public void displayInformation() {
        System.out.println(Utils.getDivider());
        String customerType = isPremiumAccount() ? AccountType.PREMIUM.getValue() : AccountType.NORMAL.getValue();
        System.out.printf("%12s |%18s|%18s|%18s\n", getCustomerId(), getName(), customerType, Utils.format(getBalance()));
        int index = 0;
        for (Account account : getAccounts()) {
            index++;
            System.out.printf("%-3s%9s |%18s|%37s\n", (index), account.getAccountNumber(), "SAVING", Utils.format(account.getBalance()));
        }
    }

    public static SavingsAccount getAccountByAccountNumber(String accountNumber) {
        String accNum;
        for (SavingsAccount account : AccountDao.list()) {
            accNum = account.accountNumber;
            if (Objects.equals(accNum, accountNumber)) {
                return account; // Trả về tài khoản khách hàng nếu tìm thấy số tài khoản
            }
        }
        return null; // Trả về null nếu không tìm thấy tài khoản nào có số tài khoản đã cho
    }

    public static void displayTransactionInformation(String customerId) {
        List<Transaction> transactions = TransactionDao.list();
        List<Customer> customerListDat = CustomerDao.list();

        if (!isCustomerExisted(customerListDat, getCustomerById(customerId))) {
            System.out.println("Không tìm thấy khách hàng, tác vụ không thành công");
        } else {
            getCustomerById(customerId).displayInformation();
            transactions.stream()
                    .filter(transaction -> getAccountByAccountNumber(transaction.getAccountNumber()).getCustomerId().equals(customerId))
                    .forEach(transaction -> {
                        System.out.printf("%-3s%7s |%18s|%37s|%37s\n","[GD] ",transaction.getAccountNumber(),transaction.getType(),Utils.format(transaction.getAmount()),transaction.getTime());
                    });
        }
    }

    public static boolean withdraw(double amount, String withDrawAccountNumber) {
        // Lấy tài khoản từ số tài khoản
        SavingsAccount account = getAccountByAccountNumber(withDrawAccountNumber);

        // Kiểm tra xem tài khoản có tồn tại không
        if (account == null) {
            System.out.println("Không tìm thấy tài khoản với số tài khoản đã cho");
            return false;
        }

        // Kiểm tra các điều kiện rút tiền
        if (amount % 10000 != 0) {
            System.out.println("Số tiền rút phải là bội số của 10.000đ (nhập lại hoặc nhập 'exit' để thoát): ");
            return false;
        }

        if (amount < MIN_BALANCE) {
            System.out.println("Nhập số tiền rút >= 50.000đ (hoặc nhập 'exit' để thoát): ");
            return false;
        }

        if (amount > account.getBalance()) {
            System.out.println("Số tiền rút vượt quá số dư tài khoản (nhập số nhỏ hơn hoặc nhập 'exit' để thoát)");
            return false;
        }

        if (account.getBalance() - amount < MIN_BALANCE) {
            System.out.println("Số dư còn lại của tài khoản tối thiểu 50.000đ sau giao dịch mời nhập số tiền nhỏ hơn (hoặc nhập 'exit' để thoát)");
            return false;
        }
        return true;
    }

    public static boolean transfer(double amount, String senderAccountNumber) {
        // Lấy tài khoản từ số tài khoản
        SavingsAccount account = getAccountByAccountNumber(senderAccountNumber);

        // Kiểm tra xem tài khoản có tồn tại không
        if (account == null) {
            System.out.println("Không tìm thấy tài khoản với số tài khoản đã cho");
            return false;
        }

        // Kiểm tra các điều kiện chuyển tiền
        if (amount % 10000 != 0) {
            System.out.println("Số tiền chuyển phải là bội số của 10.000đ (nhập lại hoặc nhập 'exit' để thoát): ");
            return false;
        }

        if (amount < MIN_BALANCE) {
            System.out.println("Nhập số tiền chuyển >= 50.000đ (hoặc nhập 'exit' để thoát): ");
            return false;
        }

        if (amount > account.getBalance()) {
            System.out.println("Số tiền chuyển vượt quá số dư tài khoản (nhập số nhỏ hơn hoặc nhập 'exit' để thoát)");
            return false;
        }

        if (account.getBalance() - amount < MIN_BALANCE) {
            System.out.println("Số dư còn lại của tài khoản tối thiểu 50.000đ sau giao dịch mời nhập số tiền nhỏ hơn (hoặc nhập 'exit' để thoát)");
            return false;
        }
        return true;
    }

    /**
     * Tính tổng số dư của tất cả các tài khoản của khách hàng
     * @return tổng số dư của tất cả các tài khoản
     */
    public double getBalance() {
        // Sử dụng phương thức getAccountsCustomerId() để lấy danh sách tài khoản của khách hàng
        // Sau đó sử dụng mapToDouble để chuyển đổi từ danh sách các tài khoản thành danh sách các số dư
        // Cuối cùng gọi phương thức sum() để tính tổng các số dư
        return this.getAccountsByCustomerId().stream().mapToDouble(Account::getBalance).sum();
    }

    public boolean isPremiumAccount() {
        for (Account account : getAccounts()) {
            if (account.isPremium()) return true;
        }
        return false;
    }

    public Account getAccountOfAccounts(String accountNumber) {
            if (isAccountExisted(this.getAccountsByCustomerId(), accountNumber)) {
                for (Account account : this.getAccountsByCustomerId()) {
                    if (Objects.equals(account.getAccountNumber(), accountNumber)) {
                        return account;
                    }
                }
            }
        return null;
    }

    @Override
    public void iAddCustomer(List<Customer> customerListDat, Customer customer) {
    }
}
