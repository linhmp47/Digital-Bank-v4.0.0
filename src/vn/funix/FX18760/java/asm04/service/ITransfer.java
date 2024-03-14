package vn.funix.FX18760.java.asm04.service;

import vn.funix.FX18760.java.asm04.models.Account;

public interface ITransfer {
     void iTransfer(Account senderOrReceiverAccount, double amount);
}
