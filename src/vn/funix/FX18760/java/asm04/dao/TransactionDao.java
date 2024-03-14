package vn.funix.FX18760.java.asm04.dao;

import vn.funix.FX18760.java.asm04.fileService.BinaryFileService;
import vn.funix.FX18760.java.asm04.models.Transaction;

import java.util.List;

public class TransactionDao {
    private static final String FILE_PATH = "store/transactions.dat";
    public static void save(List<Transaction> transactionListList) {
        BinaryFileService.writeFile(FILE_PATH, transactionListList);
    }

    public static List<Transaction> list() {
        return BinaryFileService.readFile(FILE_PATH);
    }

}
