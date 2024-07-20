package com.example.demo.service;

import com.example.demo.model.auth.Customer;
import com.example.demo.repository.ICustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Component
public class SeedDataService implements ApplicationRunner {
    private final ICustomerRepository customerRepository;

    @Autowired
    public SeedDataService(ICustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        seedCustomers();
        seedEmployees();
    }

    public void seedCustomers() {
        if (customerRepository.count() != 0)
            return;

        List<Customer> customers = Arrays.asList(
                new Customer(1, "Nguyễn Văn Anh", true, parseDate("1990-05-15"), "123 Đường 3/2, Quận 10, TP. Hồ Chí Minh", "0901234567", "nguyen.vananh@example.com", null),
                new Customer(2, "Trần Thị Mai", false, parseDate("1988-08-20"), "456 Nguyễn Thị Minh Khai, Quận 1, TP. Hồ Chí Minh", "0987654321", "tran.thimai@example.com", null),
                new Customer(3, "Phạm Thị Cúc", true, parseDate("1995-02-10"), "789 Lê Lợi, Quận 3, TP. Hồ Chí Minh", "0912345678", "pham.thicuc@example.com", null),
                new Customer(4, "Nguyễn Văn Đức", false, parseDate("1992-11-25"), "321 Cao Lỗ, Quận 8, TP. Hồ Chí Minh", "0908765432", "nguyen.vanduc@example.com", null),
                new Customer(5, "Lê Thị Lan", true, parseDate("1987-07-03"), "567 Nguyễn Công Trứ, Quận 5, TP. Hồ Chí Minh", "0976543210", "le.thilan@example.com", null),
                new Customer(6, "Trần Văn Phúc", false, parseDate("1998-04-15"), "432 Lê Văn Sỹ, Quận Tân Bình, TP. Hồ Chí Minh", "0918765433", "tran.vanphuc@example.com", null),
                new Customer(7, "Nguyễn Thị Giang", true, parseDate("1991-12-30"), "876 Nguyễn Văn Cừ, Quận 10, TP. Hồ Chí Minh", "0987654322", "nguyen.thigiang@example.com", null),
                new Customer(8, "Phạm Văn Khoa", false, parseDate("1989-09-20"), "234 Nguyễn Thị Diệu, Quận 3, TP. Hồ Chí Minh", "0909876544", "pham.vankhoa@example.com", null),
                new Customer(9, "Trần Thị Kiều", true, parseDate("1996-06-05"), "678 Nguyễn Văn Trỗi, Quận Phú Nhuận, TP. Hồ Chí Minh", "0912345679", "tran.thikieu@example.com", null),
                new Customer(10, "Nguyễn Văn Lâm", false, parseDate("1993-03-18"), "987 Nguyễn Đình Chiểu, Quận 3, TP. Hồ Chí Minh", "0987654323", "nguyen.vanlam@example.com", null),
                new Customer(11, "Lê Thị Hồng", true, parseDate("1994-08-28"), "543 Lê Hồng Phong, Quận 10, TP. Hồ Chí Minh", "0901234568", "le.thihong@example.com", null),
                new Customer(12, "Phạm Văn Minh", false, parseDate("1986-05-12"), "321 Nguyễn Hữu Cầu, Quận 1, TP. Hồ Chí Minh", "0976543211", "pham.vanminh@example.com", null),
                new Customer(13, "Nguyễn Thị Quyên", true, parseDate("1997-02-25"), "876 Lý Tự Trọng, Quận 3, TP. Hồ Chí Minh", "0912345680", "nguyen.thiquyen@example.com", null),
                new Customer(14, "Trần Văn Quang", false, parseDate("1990-11-10"), "456 Nguyễn Thị Định, Quận 7, TP. Hồ Chí Minh", "0909876545", "tran.vanquang@example.com", null),
                new Customer(15, "Nguyễn Thị Phương", true, parseDate("1988-07-20"), "234 Nguyễn Văn Bảo, Quận Bình Thạnh, TP. Hồ Chí Minh", "0987654324", "nguyen.thiphuong@example.com", null),
                new Customer(16, "Lê Văn Quyền", false, parseDate("1995-04-15"), "678 Lê Văn Lương, Quận 7, TP. Hồ Chí Minh", "0912345681", "le.vanquyen@example.com", null),
                new Customer(17, "Phạm Thị Rêu", true, parseDate("1992-12-30"), "987 Nguyễn Thị Minh Khai, Quận 3, TP. Hồ Chí Minh", "0901234569", "pham.thire@example.com", null),
                new Customer(18, "Nguyễn Văn Sơn", false, parseDate("1991-09-20"), "543 Cao Lỗ, Quận Tân Phú, TP. Hồ Chí Minh", "0976543212", "nguyen.vanson@example.com", null),
                new Customer(19, "Trần Thị Trâm", true, parseDate("1998-06-05"), "876 Nguyễn Thị Thập, Quận 11, TP. Hồ Chí Minh", "0912345682", "tran.thitram@example.com", null),
                new Customer(20, "Nguyễn Văn Tuấn", false, parseDate("1987-03-18"), "321 Nguyễn Văn Nghi, Quận Gò Vấp, TP. Hồ Chí Minh", "0987654325", "nguyen.vantuan@example.com", null)
        );

        customerRepository.saveAll(customers);
    }

    public void seedEmployees() {

    }

    private Date parseDate(String dateStr) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd").parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace(); // Handle the exception appropriately
            return null;
        }
    }
}
