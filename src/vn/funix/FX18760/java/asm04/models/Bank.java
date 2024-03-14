package vn.funix.FX18760.java.asm04.models;

import vn.funix.FX18760.java.asm04.dao.CustomerDao;
import vn.funix.FX18760.java.asm04.exception.CustomerIdNotValidException;
import vn.funix.FX18760.java.asm04.service.ICustomer;

import java.util.List;

import static vn.funix.FX18760.java.asm04.models.DigitalBank.isCustomerExisted;

public class Bank implements ICustomer {
    protected String bankId;
    protected String bankName;
    @Override
    public void iAddCustomer(List<Customer> customerListDat, Customer customer) {
            try {
                if (isCustomerExisted(customerListDat, customer)) {
                    throw new CustomerIdNotValidException("Khách hàng "+ customer.getCustomerId()+ " đã tồn tại, thêm khách hàng không thành công");
                } else {
                    System.out.println("Đã thêm khách hàng "+ customer.getCustomerId() + " vào danh sách khách hàng");
                    customerListDat.add(customer);
                    CustomerDao.save(customerListDat);
                }
            } catch (CustomerIdNotValidException e) {
                System.out.println(e.getMessage());
            }
        }

    }
