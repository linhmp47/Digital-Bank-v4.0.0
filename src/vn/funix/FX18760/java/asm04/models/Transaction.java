package vn.funix.FX18760.java.asm04.models;

import vn.funix.FX18760.java.Utils;
import vn.funix.FX18760.java.asm04.enums.TransactionType;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

/**
 * Quản lý giao dịch
 */

public class Transaction implements Serializable {
    @Serial
    private final static long serialVersionUID = 1L;
    private String id;
    private String accountNumber;
    private double amount;
    private String time;
    private boolean status;
    private TransactionType type;

    public Transaction(String accountNumber, double amount, boolean status, TransactionType type) {
            this.id=UUID.randomUUID().toString();
            this.accountNumber=accountNumber;
            this.amount=amount;
            this.time= Utils.getDateTime();
            this.status=status;
            this.type = type;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public double getAmount() {
        return amount;
    }

    public String getTime() {
        return time;
    }

    public TransactionType getType() {
        return type;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id='" + id + '\'' +
                ", accountNumber='" + accountNumber + '\'' +
                ", amount=" + amount +
                ", time='" + time + '\'' +
                ", status=" + status +
                ", type=" + type +
                '}';
    }

}
