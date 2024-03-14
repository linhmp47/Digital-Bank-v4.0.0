package vn.funix.FX18760.java.asm04.unittest;

import org.junit.jupiter.api.Test;
import vn.funix.FX18760.java.asm04.fileService.TextFileService;
import vn.funix.FX18760.java.asm04.models.Customer;

import java.io.IOException;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TextFileServiceTest {
    @Test
    public void testReadFile() {
        String fileName = "store/customers.txt"; // Replace with your test file name
        try {
            List<Customer> customers = TextFileService.readFile(fileName);
            // Ensure that the number of customers read is as expected
            assertEquals(8, customers.size());

            // Assert the properties of the first customer
            Customer firstCustomer = customers.get(0);
            assertEquals("Nguyen Van A", firstCustomer.getName());
            assertEquals("064091012001", firstCustomer.getCustomerId());

            // Assert the properties of the second customer
            Customer secondCustomer = customers.get(1);
            assertEquals("Nguyen Van B", secondCustomer.getName());
            assertEquals("064091012002", secondCustomer.getCustomerId());

            // Assert the properties of the third customer
            Customer thirdCustomer = customers.get(2);
            assertEquals("Nguyen Van C", thirdCustomer.getName()); // Assuming the name is set to the error message
            assertEquals("064091012003", thirdCustomer.getCustomerId()); // Assuming the ID is set to the invalid ID
        } catch (IOException e) {
            // Handle IOException
            e.printStackTrace();
        }
    }
}
