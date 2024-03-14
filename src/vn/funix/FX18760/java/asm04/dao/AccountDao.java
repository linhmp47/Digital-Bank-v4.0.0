package vn.funix.FX18760.java.asm04.dao;

import vn.funix.FX18760.java.asm04.fileService.BinaryFileService;
import vn.funix.FX18760.java.asm04.models.Account;
import vn.funix.FX18760.java.asm04.models.SavingsAccount;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AccountDao {
    private static final String FILE_PATH = "store/accounts.dat";
    private static final int MAX_THREAD = 10; // Xác định số lượng luồng tối đa mong muốn
    public static void save(List<SavingsAccount> accountList) {
        BinaryFileService.writeFile(FILE_PATH, accountList);
    }

    public static List<SavingsAccount> list() {
        return BinaryFileService.readFile(FILE_PATH);
    }
    public static void update(Account editAccount) {
        ExecutorService executorService = Executors.newFixedThreadPool(MAX_THREAD);

        executorService.execute(() -> {
            List<SavingsAccount> accounts = list();
            boolean hasExists = accounts.stream().anyMatch(account -> account.getAccountNumber().equals(editAccount.getAccountNumber()));

            List<SavingsAccount> updateAccounts;

            if (!hasExists) {
                updateAccounts = new ArrayList<>(accounts);
                updateAccounts.add((SavingsAccount) editAccount);
            } else {
                updateAccounts = new ArrayList<>();

                for (Account account : accounts) {
                    if (account.getAccountNumber().equals(editAccount.getAccountNumber())) {
                        updateAccounts.add((SavingsAccount) editAccount);
                    } else {
                        updateAccounts.add((SavingsAccount) account);
                    }
                }
            }
            save(updateAccounts);
        });

        executorService.shutdown();
    }
}
