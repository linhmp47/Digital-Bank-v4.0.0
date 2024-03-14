package vn.funix.FX18760.java.asm04.unittest;

import org.junit.Test;
import vn.funix.FX18760.java.asm04.models.SavingsAccount;

import static org.junit.Assert.*;

public class SavingsAccountTest {
    @Test
    public void testIsAccepted_ValidAmount() {
        SavingsAccount savingsAccount = new SavingsAccount("123456789", 100000, "customerId");

        // Kiểm tra với một số tiền hợp lệ
        assertTrue(savingsAccount.isAccepted(50000)); // Giả sử số tiền tối thiểu đã đạt, và số dư đủ
    }

    @Test
    public void testIsAccepted_InvalidAmount() {
        SavingsAccount savingsAccount = new SavingsAccount("123456789", 5000000, "customerId");

        // Kiểm tra với một số tiền không hợp lệ (dưới mức tối thiểu)
        assertFalse(savingsAccount.isAccepted(40000)); // Giả sử số tiền tối thiểu không đạt được

        // Kiểm tra với một số tiền không hợp lệ (không phải bội số của 10000)
        assertFalse(savingsAccount.isAccepted(500001)); // Giả sử không phải là bội số của 10000

        // Kiểm tra với một số tiền không hợp lệ (vượt quá giới hạn rút tối đa)
        assertFalse(savingsAccount.isAccepted(6000000)); // Giả sử số tiền vượt quá giới hạn rút tối đa
    }

}