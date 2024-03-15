package vn.funix.FX18760.java.asm04.models;

import vn.funix.FX18760.java.Utils;
import vn.funix.FX18760.java.asm04.dao.AccountDao;
import vn.funix.FX18760.java.asm04.dao.TransactionDao;
import vn.funix.FX18760.java.asm04.service.IReport;
import vn.funix.FX18760.java.asm04.service.ITransaction;
import vn.funix.FX18760.java.asm04.service.ITransfer;
import vn.funix.FX18760.java.asm04.service.IWithdraw;

import java.io.Serializable;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import static vn.funix.FX18760.java.asm04.models.DigitalBank.getCustomerById;

/**
 * Quản lý thông tin tài khoản khách hàng
 */

public abstract class Account implements Serializable, ITransaction, IReport, ITransfer, IWithdraw {
    protected static final long serialVersionUID = 0L;
    protected String accountNumber;
    protected double balance;
    protected String customerId;
    static List<Transaction> transactions = TransactionDao.list();

    public Account(String accountNumber, double balance, String customerId) {
        this.accountNumber=accountNumber;
        this.balance=balance;
        this.customerId = customerId;
    }

    public Account(String accountNumber, double balance) {
        this.accountNumber=accountNumber;
        this.balance=balance;
    }

    public List<SavingsAccount> getList() {
        return AccountDao.list();
    }
    public String getCustomerId() {
        return customerId;
    }
    public  String getAccountNumber(){
        return accountNumber;
    }
    public double getBalance() {
        return balance;
    }
    public void setBalance(double balance) {
        this.balance = balance;
    }
    public boolean isPremium(){
        return this.balance >= Utils.MIN_PREMIUM_BALANCE;
    }
    public static boolean minBalance(double balance){
        return balance >= 50000;
    }

    public List<Transaction> getTransactions() {
        return this.getList().stream()
                .flatMap(account -> account.getTransactions().stream())
                .filter(transaction -> this.accountNumber.equals(transaction.getAccountNumber()))
                .collect(Collectors.toList());
    }

    public Customer getCustomer() {
        return getCustomerByAccountNumber(this.accountNumber);
    }

    private Customer getCustomerByAccountNumber(String accountNumber) {
        for (Account account : AccountDao.list()) {
            if (account.getCustomerId().equals(customerId)) {
                return getCustomerById(account.customerId) ; // Trả về tài khoản nếu tìm thấy
            }
        }
        return null; // Trả về null nếu không tìm thấy tài khoản nào có customerId đã cho
    }

    public static void input(Scanner scanner){
    }
    public abstract boolean isAccepted(double amount);

}
