package vn.funix.FX18760.java.asm04.service;

import vn.funix.FX18760.java.asm04.enums.TransactionType;

public interface ITransaction {
    void iCreateTransaction(String AccountNumber,double amount,boolean status, TransactionType type);

}
