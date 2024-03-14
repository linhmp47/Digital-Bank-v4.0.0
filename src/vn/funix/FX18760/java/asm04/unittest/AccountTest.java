package vn.funix.FX18760.java.asm04.unittest;

import org.junit.jupiter.api.Test;
import vn.funix.FX18760.java.asm04.enums.TransactionType;
import vn.funix.FX18760.java.asm04.models.Account;

import static org.junit.jupiter.api.Assertions.*;

class AccountTest {
    @Test
    void isPremium() {
        Account account = new Account("123456", 50000000)
        {
            @Override
            public void iWithdraw(Account withdrawAccount, double amount) {
            }
            @Override
            public void iTransfer(Account senderOrReceiverAccount, double amount) {
            }
            @Override
            public void iCreateTransaction(String AccountNumber, double amount, boolean status, TransactionType type) {
            }
            @Override
            public void log(double amount) {
            }
            @Override
            public void log(Account senderAccount, Account receiverAccount, double amount) {
            }
            @Override
            public boolean isAccepted(double amount) {
                return false;
            }
        };
        assertTrue(account.isPremium());
    }

    @Test
    void minBalance() {
        Account accountMinBalance = new Account("456789", 50000) {
            @Override
            public void iWithdraw(Account withdrawAccount, double amount) {
            }
            @Override
            public void iTransfer(Account senderOrReceiverAccount, double amount) {
            }
            @Override
            public void iCreateTransaction(String AccountNumber, double amount, boolean status, TransactionType type) {
            }
            @Override
            public void log(double amount) {
            }
            @Override
            public void log(Account senderAccount, Account receiverAccount, double amount) {
            }
            @Override
            public boolean isAccepted(double amount) {
                return false;
            }
        };
        assertTrue(accountMinBalance.minBalance(accountMinBalance.getBalance()));
    }

}