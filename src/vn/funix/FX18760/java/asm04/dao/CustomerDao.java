package vn.funix.FX18760.java.asm04.dao;

import vn.funix.FX18760.java.asm04.fileService.BinaryFileService;
import vn.funix.FX18760.java.asm04.models.Customer;

import java.util.List;

public class CustomerDao {
    private static final String FILE_PATH = "store/customers.dat";
    public static void save(List<Customer> customerList) {
        BinaryFileService.writeFile(FILE_PATH, customerList);
    }

    public static List<Customer> list() {
        return BinaryFileService.readFile(FILE_PATH);
    }
}
