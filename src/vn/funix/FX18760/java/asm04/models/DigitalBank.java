package vn.funix.FX18760.java.asm04.models;

import vn.funix.FX18760.java.Utils;
import vn.funix.FX18760.java.asm04.dao.CustomerDao;
import vn.funix.FX18760.java.asm04.enums.TransactionType;
import vn.funix.FX18760.java.asm04.exception.ExistsLstException;
import vn.funix.FX18760.java.asm04.fileService.TextFileService;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

import static vn.funix.FX18760.java.asm04.Asm04.menuChoices;
import static vn.funix.FX18760.java.asm04.models.Customer.getAccountByAccountNumber;

public class DigitalBank extends Bank implements Serializable {
    /**
     * showCustomers(): 1. Xem danh sách khách hàng
     */
    public void showCustomers() {
        List<Customer> customers = CustomerDao.list();

        try {
            if(Utils.checkNullPointerLst(customers))
                throw new ExistsLstException("Chưa có khách hàng nào trong danh sách!");
        } catch (ExistsLstException e) {
            System.out.println(e.getMessage());
        }

        for (Customer cus: customers) {
            cus.displayInformation();
        }
    }

    /**
     * addCustomers(String fileName): 2. Nhập danh sách khách hàng
     * @param fileName
     * @throws IOException
     */
    public void addCustomers(String fileName) throws IOException {
        List<Customer> customerListTxt = TextFileService.readFile(fileName);
        List<Customer> customerListDat = CustomerDao.list();

        for (Customer customer: customerListTxt) {
            iAddCustomer(customerListDat, customer);
        }
    }

    /**
     * addSavingAccount(Scanner scanner, String customerId): 5. Rút tiền"
     * @param scanner
     * @param customerId
     */
    public static void addSavingAccount(Scanner scanner, String customerId) {
        List<Customer> customerListDat = CustomerDao.list();
        String accountNumber;

        if (!isCustomerExisted(customerListDat, getCustomerById(customerId))) {
            System.out.println("Không tìm thấy khách hàng " + customerId + " ,tác vụ không thành công");
            menuChoices();
        }
        else {

            while (true) {
                System.out.println("Nhập số tài khoản gồm 6 chữ số: ");
                accountNumber = Utils.getAccountNumber();
                if (getAccountByAccountNumber(accountNumber) != null) {
                    System.out.println("Số tài khoản này đã tồn tại trong hệ thống.");
                } else break;
            }

            while (true) {
                System.out.println("Nhập số dư tài khoản >= 50000đ: ");
                double initialDeposit = Utils.getToDouble();

                if (initialDeposit < Utils.MIN_BALANCE) {
                    continue;
                }

                if (initialDeposit % 10000 !=0 ){
                    System.out.println("Số tiền gửi phải là bội số của 10.000đ (nhập lại hoặc nhập 'exit' để thoát): ");
                    continue;
                }

                boolean status = true;

                SavingsAccount savingAccount = new SavingsAccount(accountNumber, initialDeposit, customerId);

                savingAccount.iSaveSavingAccount(savingAccount);

                System.out.println("Tạo tài khoản thành công!");

                savingAccount.iCreateTransaction(accountNumber,+initialDeposit,status, TransactionType.DEPOSIT);

                break;
            }

        }

    }

    public void transfers(Scanner scanner, String customerId) {
        List<Customer> customerListDat = CustomerDao.list();
        String senderAccountNumber;
        String receiverAccountNumber;
        double amount;
        String yesNo;

        if (!isCustomerExisted(customerListDat, getCustomerById(customerId))) {
            System.out.println("Không tìm thấy khách hàng, tác vụ không thành công");
        }
        else {

            while (true) {
                Customer customerTransfer = getCustomerById(customerId);
                customerTransfer.displayInformation();

                System.out.println("Nhập số tài khoản ");
                senderAccountNumber = Utils.getAccountNumber();

                if (customerTransfer.getAccountOfAccounts(senderAccountNumber)==null) {
                    System.out.println("Số tài khoản này không thuộc khách hàng: " + customerId);
                } else break;
            }

            while (true) {
                System.out.println("Nhập số tài khoản nhận (exit để thoát): ");
                receiverAccountNumber = Utils.getAccountNumber();

                if (getAccountByAccountNumber(receiverAccountNumber) == null) {
                    System.out.println("Số tài khoản này không tồn tại trong hệ thống.");
                    continue;
                }

                if (Objects.equals(receiverAccountNumber, senderAccountNumber)) {
                    System.out.println("Số tài khoản nhận trùng với số tài khoản chuyển");
                    continue;
                }

                System.out.println("Gửi tiền đến tài khoản "+receiverAccountNumber+" | "+ getCustomerById(getAccountByAccountNumber(receiverAccountNumber).getCustomerId()).getName());
                break;
            }

            while (true) {
                System.out.println("Nhập số tiền chuyển: ");
                amount = Utils.getToDouble();

                if(!Customer.transfer(amount, senderAccountNumber)) {
                    continue;
                }
                if(Customer.transfer(amount, senderAccountNumber)) {
                    break;
                }
            }

            while (true) {
                System.out.println("Xác nhận thực hiện chuyển "+Utils.format(amount)+ " từ tài khoản ["+senderAccountNumber+"] đến tài khoản ["+ receiverAccountNumber+"] (Y/N)");
                yesNo = Utils.getYesNo().toLowerCase();
                boolean status = Objects.equals(yesNo, "y");

                if (status) {
                    SavingsAccount senderAccount = getAccountByAccountNumber(senderAccountNumber);
                    SavingsAccount receiverAccount = getAccountByAccountNumber(receiverAccountNumber);

                    senderAccount.iTransfer(senderAccount,-amount);
                    receiverAccount.iTransfer(receiverAccount,+amount);

                    System.out.println("Chuyển tiền thành công, biên lai giao dịch!");

                    senderAccount.iCreateTransaction(senderAccount.getAccountNumber(),-amount,status, TransactionType.TRANSFER);
                    receiverAccount.iCreateTransaction(receiverAccount.getAccountNumber(),+amount,status, TransactionType.TRANSFER);

                    senderAccount.log(senderAccount, receiverAccount, amount);
                    break;
                }

                if (Objects.equals(yesNo, "n")) {
                    System.out.println("Hủy bỏ giao dịch ");
                    break;
                }
            }
        }

    }

    public static void withdraw(Scanner scanner, String customerId) {
        List<Customer> customerListDat = CustomerDao.list();
        String withDrawAccountNumber;
        double amount;
        String yesNo;

        if (!isCustomerExisted(customerListDat, getCustomerById(customerId))) {
            System.out.println("Không tìm thấy khách hàng, tác vụ không thành công");
        }
        else {
            while (true) {
                Customer customerWithdraw = getCustomerById(customerId);
                customerWithdraw.displayInformation();

                System.out.println("Nhập số tài khoản ");
                withDrawAccountNumber = Utils.getAccountNumber();
                if (customerWithdraw.getAccountOfAccounts(withDrawAccountNumber) == null) {
                    System.out.println("Số tài khoản này không thuộc khách hàng có CCCD: " + customerId);
                } else break;
            }

            while (true) {
                System.out.println("Nhập số tiền rút: ");
                amount = Utils.getToDouble();
                if(!Customer.withdraw(amount, withDrawAccountNumber)) {
                    continue;
                }
                if(Customer.withdraw(amount, withDrawAccountNumber)) {
                    break;
                }
            }

            while (true) {
                System.out.println("Xác nhận rút tiền "+Utils.format(amount)+ " từ tài khoản ["+withDrawAccountNumber+"] (Y/N)");
                yesNo = Utils.getYesNo().toLowerCase();
                boolean status = Objects.equals(yesNo, "y");

                if (status) {
                    Account withdrawAccount = getAccountByAccountNumber(withDrawAccountNumber);
                    withdrawAccount.iWithdraw(withdrawAccount, amount);

                    System.out.println("Rút tiền thành công, biên lai giao dịch!");
                    withdrawAccount.iCreateTransaction(withdrawAccount.getAccountNumber(),-amount,status, TransactionType.WITHDRAW);
                    withdrawAccount.log(amount);
                    break;
                }

                if (Objects.equals(yesNo, "n")) {
                    System.out.println("Hủy bỏ giao dịch ");
                    break;
                }
            }
        }

    }

    public static boolean isCustomerExisted(List<Customer> customersFileDat, Customer customer) {
        if (customer == null) {
            return false;
        } else {
            return customersFileDat.stream()
                    .anyMatch(existingCustomer -> existingCustomer.getCustomerId().equals(customer.getCustomerId()));
        }
    }

    public static boolean isAccountExisted(List<Account> accountsList, String accountNumber) {
        return accountsList.stream()
                .anyMatch(existingAccount -> existingAccount.getAccountNumber().equals(accountNumber));
    }

    public static Customer getCustomerById(String customerId){
        for (Customer customer : CustomerDao.list()){
            if(customer.getCustomerId().equals(customerId)){
                return customer;
            }
        }
        return null;
    }

}
