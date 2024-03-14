package vn.funix.FX18760.java.asm04.models;

import vn.funix.FX18760.java.Utils;
import vn.funix.FX18760.java.asm04.dao.AccountDao;
import vn.funix.FX18760.java.asm04.dao.TransactionDao;
import vn.funix.FX18760.java.asm04.enums.TransactionType;
import vn.funix.FX18760.java.asm04.service.IAccount;
import vn.funix.FX18760.java.asm04.service.IReport;
import vn.funix.FX18760.java.asm04.service.ITransfer;
import vn.funix.FX18760.java.asm04.service.IWithdraw;

import java.io.Serializable;
import java.util.List;

import static vn.funix.FX18760.java.asm04.dao.AccountDao.update;

public class SavingsAccount extends Account implements Serializable, IReport, ITransfer, IWithdraw, IAccount {
    @java.io.Serial
    private static final long serialVersionUID = 0L;
    private final double SAVINGS_ACCOUNT_MAX_WITHDRAW = 5000000;
    public SavingsAccount(String accountNumber, double balance, String customerId) {
        super(accountNumber, balance, customerId);
    }

    @Override
    public String toString() {
        return String.format("%13s%-8s%2s%28.2f%s\n",super.getAccountNumber()+" | ","SAVINGS"," | ",super.getBalance(),"đ.");

    }

    @Override
    public boolean isAccepted(double amount) {
        double minAmount = 50000;
        if (minBalance((getBalance()-amount)) && amount%10000==0 && amount>=minAmount){
            if(!isPremium()){
                if(amount <= SAVINGS_ACCOUNT_MAX_WITHDRAW){
                    return true;
                }else {
                    System.out.println("Khong the rut tren 5,000,000 tren mot lan giao dich. Vui long thu lai.");
                }
            }else {
                return true;
            }
        }else {
            if(!minBalance(getBalance() - amount)){
                System.out.println("So du sau khi rut duoi 50,000. Vui long thu lai sau. ");
            }
            else if(amount%10000!=0){
                System.out.println("So tien rut phai la boi cua 10,000. Vui long thu lai sau. ");
            }
            else if (amount<minAmount){
                System.out.println("So tien rut toi thieu 50,000.Vui long thu lai sau.");
            }
        }
        return false;
    }

    @Override
    public void log(double amount) {
        System.out.println(Utils.getDivider());
        System.out.printf("%30s%n",Utils.getTitle()+" SAVING");
        System.out.printf("NGAY G/D: %28s%n",Utils.getDateTime());
        System.out.printf("ATM ID: %30s%n","DIGITAL-BANK-ATM 2022");
        System.out.printf("SO TK: %31s%n",getAccountNumber());
        System.out.printf("SO TIEN: %29s%n", Utils.format(amount));
        System.out.printf("SO DU: %31s%n", Utils.format(getBalance()));
        System.out.printf("PHI + VAT: %27s%n", Utils.format(0));
        System.out.println(Utils.getDivider());
    }

    @Override
    public void log(Account senderAccount, Account receiverAcount, double amount) {
        System.out.println(Utils.getDivider());
        System.out.printf("| %53s%n",Utils.getTitle()+" SAVING                 |");
        System.out.printf("| NGAY G/D: %43s%n",Utils.getDateTime()+" |");
        System.out.printf("| ATM ID: %45s%n","DIGITAL-BANK-ATM 2022"+" |");
        System.out.printf("| SO TK: %46s%n",senderAccount.getAccountNumber()+" |");
        System.out.printf("| SO TK NHAN: %41s%n",receiverAcount.getAccountNumber()+" |");
        System.out.printf("| SO TIEN: %44s%n", Utils.format(amount)+" |");
        System.out.printf("| SO DU: %46s%n", Utils.format(senderAccount.getBalance())+" |");
        System.out.printf("| PHI + VAT: %42s%n", Utils.format(0)+" |");
        System.out.println(Utils.getDivider());
    }

    @Override
    public void iTransfer(Account senderOrReceiverAccount, double amount) {
        senderOrReceiverAccount.setBalance(senderOrReceiverAccount.getBalance()+amount);
        update(senderOrReceiverAccount);
    }

    @Override
    public void iWithdraw(Account withdrawAccount, double amount) {
        withdrawAccount.setBalance(withdrawAccount.getBalance()-amount);
        update(withdrawAccount);
    }

    @Override
    public void iCreateTransaction(String AccountNumber, double amount, boolean status, TransactionType type) {
        transactions.add(new Transaction(accountNumber,amount,status,type));
        TransactionDao.save(transactions);
    }

    @Override
    public void iSaveSavingAccount(SavingsAccount savingAccount) {
        List<SavingsAccount> savingAccountList = AccountDao.list();
        savingAccountList.add(savingAccount);
        AccountDao.save(savingAccountList);
    }
}
