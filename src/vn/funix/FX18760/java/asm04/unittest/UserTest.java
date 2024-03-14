package vn.funix.FX18760.java.asm04.unittest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import vn.funix.FX18760.java.asm04.models.User;

class UserTest {
    @Test
    void isValidId() {
        User user = new User("Linh", "064091012100");
        Assertions.assertTrue(User.isValidId(user.getCustomerId()));
    }
}