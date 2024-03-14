package vn.funix.FX18760.java.asm04.service;

import vn.funix.FX18760.java.asm04.models.Account;

public interface IReport {
    void log(double amount);
    void log(Account senderAccount, Account receiverAccount, double amount);
}
