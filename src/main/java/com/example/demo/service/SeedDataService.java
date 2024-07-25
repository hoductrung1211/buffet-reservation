package com.example.demo.service;

import com.example.demo.model.auth.Account;
import com.example.demo.model.auth.Customer;
import com.example.demo.model.auth.Employee;
import com.example.demo.model.auth.Role;
import com.example.demo.model.menu.MenuItem;
import com.example.demo.model.menu.MenuItemCategory;
import com.example.demo.model.menu.MenuItemGroup;
import com.example.demo.model.price.DayGroup;
import com.example.demo.model.price.DayGroupApplication;
import com.example.demo.model.price.Price;
import com.example.demo.model.table.BuffetTable;
import com.example.demo.model.table.TableGroup;
import com.example.demo.repository.*;
import com.example.demo.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Component
public class SeedDataService implements ApplicationRunner {
    // Authentication
    private final ICustomerRepository customerRepository;
    private final IEmployeeRepository employeeRepository;
    private final IAccountRepository accountRepository;
    // Table
    private final ITableGroupRepository tableGroupRepository;
    private final IBuffetTableRepository buffetTableRepository;
    // Price
    private final IPriceRepository priceRepository;
    private final IDayGroupRepository dayGroupRepository;
    private final IDayGroupApplicationRepository dayGroupApplicationRepository;
    // Menu
    private final IMenuItemCategoryRepository menuItemCategoryRepository;
    private final IMenuItemRepository menuItemRepository;

    @Autowired
    public SeedDataService(ICustomerRepository customerRepository, IEmployeeRepository employeeRepository, IAccountRepository accountRepository, ITableGroupRepository tableGroupRepository, IBuffetTableRepository buffetTableRepository, IPriceRepository priceRepository, IDayGroupRepository dayGroupRepository, IDayGroupApplicationRepository dayGroupApplicationRepository, IMenuItemCategoryRepository menuItemCategoryRepository, IMenuItemRepository menuItemRepository) {
        this.customerRepository = customerRepository;
        this.employeeRepository = employeeRepository;
        this.accountRepository = accountRepository;
        this.tableGroupRepository = tableGroupRepository;
        this.buffetTableRepository = buffetTableRepository;
        this.priceRepository = priceRepository;
        this.dayGroupRepository = dayGroupRepository;
        this.dayGroupApplicationRepository = dayGroupApplicationRepository;
        this.menuItemCategoryRepository = menuItemCategoryRepository;
        this.menuItemRepository = menuItemRepository;
    }

    @Override
    public void run(ApplicationArguments args) {
        // Authentication
        seedCustomers();
        seedEmployees();

        // Table
        seedTables();

        // Menu
        seedMenuItemCategories();

        // Price
        seedPrices();
    }

    /* Authentication */
    public void seedCustomers() {
        if (customerRepository.count() != 0)
            return;

        var accounts = Arrays.asList(
                new Account(1, "123456", Role.CUSTOMER, true),
                new Account(2, "123456", Role.CUSTOMER, true),
                new Account(3, "123456", Role.CUSTOMER, true),
                new Account(4, "123456", Role.CUSTOMER, true),
                new Account(5, "123456", Role.CUSTOMER, true),
                new Account(6, "123456", Role.CUSTOMER, true),
                new Account(7, "123456", Role.CUSTOMER, true),
                new Account(8, "123456", Role.CUSTOMER, true),
                new Account(9, "123456", Role.CUSTOMER, true),
                new Account(10, "123456", Role.CUSTOMER, true),
                new Account(11, "123456", Role.CUSTOMER, true),
                new Account(12, "123456", Role.CUSTOMER, true),
                new Account(13, "123456", Role.CUSTOMER, true),
                new Account(14, "123456", Role.CUSTOMER, true),
                new Account(15, "123456", Role.CUSTOMER, true),
                new Account(16, "123456", Role.CUSTOMER, true),
                new Account(17, "123456", Role.CUSTOMER, true),
                new Account(18, "123456", Role.CUSTOMER, true),
                new Account(19, "123456", Role.CUSTOMER, true),
                new Account(20, "123456", Role.CUSTOMER, true)
        );

        accountRepository.saveAll(accounts);

        List<Customer> customers = Arrays.asList(
                new Customer(1, "Nguyễn Văn Anh", true, DateUtil.parseDate("1990-05-15"), "123 Đường 3/2, Quận 10, TP. Hồ Chí Minh", "0901234567", "nguyen.vananh@example.com", accounts.get(0)),
                new Customer(2, "Trần Thị Mai", false, DateUtil.parseDate("1988-08-20"), "456 Nguyễn Thị Minh Khai, Quận 1, TP. Hồ Chí Minh", "0987654321", "tran.thimai@example.com", accounts.get(1)),
                new Customer(3, "Phạm Thị Cúc", true, DateUtil.parseDate("1995-02-10"), "789 Lê Lợi, Quận 3, TP. Hồ Chí Minh", "0912345678", "pham.thicuc@example.com", accounts.get(2)),
                new Customer(4, "Nguyễn Văn Đức", false, DateUtil.parseDate("1992-11-25"), "321 Cao Lỗ, Quận 8, TP. Hồ Chí Minh", "0908765432", "nguyen.vanduc@example.com", accounts.get(3)),
                new Customer(5, "Lê Thị Lan", true, DateUtil.parseDate("1987-07-03"), "567 Nguyễn Công Trứ, Quận 5, TP. Hồ Chí Minh", "0976543210", "le.thilan@example.com", accounts.get(4)),
                new Customer(6, "Trần Văn Phúc", false, DateUtil.parseDate("1998-04-15"), "432 Lê Văn Sỹ, Quận Tân Bình, TP. Hồ Chí Minh", "0918765433", "tran.vanphuc@example.com", accounts.get(5)),
                new Customer(7, "Nguyễn Thị Giang", true, DateUtil.parseDate("1991-12-30"), "876 Nguyễn Văn Cừ, Quận 10, TP. Hồ Chí Minh", "0987654322", "nguyen.thigiang@example.com", accounts.get(6)),
                new Customer(8, "Phạm Văn Khoa", false, DateUtil.parseDate("1989-09-20"), "234 Nguyễn Thị Diệu, Quận 3, TP. Hồ Chí Minh", "0909876544", "pham.vankhoa@example.com", accounts.get(7)),
                new Customer(9, "Trần Thị Kiều", true, DateUtil.parseDate("1996-06-05"), "678 Nguyễn Văn Trỗi, Quận Phú Nhuận, TP. Hồ Chí Minh", "0912345679", "tran.thikieu@example.com", accounts.get(8)),
                new Customer(10, "Nguyễn Văn Lâm", false, DateUtil.parseDate("1993-03-18"), "987 Nguyễn Đình Chiểu, Quận 3, TP. Hồ Chí Minh", "0987654323", "nguyen.vanlam@example.com", accounts.get(9)),
                new Customer(11, "Lê Thị Hồng", true, DateUtil.parseDate("1994-08-28"), "543 Lê Hồng Phong, Quận 10, TP. Hồ Chí Minh", "0901234568", "le.thihong@example.com", accounts.get(10)),
                new Customer(12, "Phạm Văn Minh", false, DateUtil.parseDate("1986-05-12"), "321 Nguyễn Hữu Cầu, Quận 1, TP. Hồ Chí Minh", "0976543211", "pham.vanminh@example.com", accounts.get(11)),
                new Customer(13, "Nguyễn Thị Quyên", true, DateUtil.parseDate("1997-02-25"), "876 Lý Tự Trọng, Quận 3, TP. Hồ Chí Minh", "0912345680", "nguyen.thiquyen@example.com", accounts.get(12)),
                new Customer(14, "Trần Văn Quang", false, DateUtil.parseDate("1990-11-10"), "456 Nguyễn Thị Định, Quận 7, TP. Hồ Chí Minh", "0909876545", "tran.vanquang@example.com", accounts.get(13)),
                new Customer(15, "Nguyễn Thị Phương", true, DateUtil.parseDate("1988-07-20"), "234 Nguyễn Văn Bảo, Quận Bình Thạnh, TP. Hồ Chí Minh", "0987654324", "nguyen.thiphuong@example.com", accounts.get(14)),
                new Customer(16, "Lê Văn Quyền", false, DateUtil.parseDate("1995-04-15"), "678 Lê Văn Lương, Quận 7, TP. Hồ Chí Minh", "0912345681", "le.vanquyen@example.com", accounts.get(15)),
                new Customer(17, "Phạm Thị Rêu", true, DateUtil.parseDate("1992-12-30"), "987 Nguyễn Thị Minh Khai, Quận 3, TP. Hồ Chí Minh", "0901234569", "pham.thire@example.com", accounts.get(16)),
                new Customer(18, "Nguyễn Văn Sơn", false, DateUtil.parseDate("1991-09-20"), "543 Cao Lỗ, Quận Tân Phú, TP. Hồ Chí Minh", "0976543212", "nguyen.vanson@example.com", accounts.get(17)),
                new Customer(19, "Trần Thị Trâm", true, DateUtil.parseDate("1998-06-05"), "876 Nguyễn Thị Thập, Quận 11, TP. Hồ Chí Minh", "0912345682", "tran.thitram@example.com", accounts.get(18)),
                new Customer(20, "Nguyễn Văn Tuấn", false, DateUtil.parseDate("1987-03-18"), "321 Nguyễn Văn Nghi, Quận Gò Vấp, TP. Hồ Chí Minh", "0987654325", "nguyen.vantuan@example.com", accounts.get(19))
        );

        customerRepository.saveAll(customers);
    }

    public void seedEmployees() {
        if (employeeRepository.count() != 0) {
            return;
        }

        var accounts = Arrays.asList(
                new Account(21, "123456", Role.EMPLOYEE, true),
                new Account(22, "123456", Role.EMPLOYEE, true),
                new Account(23, "123456", Role.EMPLOYEE, true),
                new Account(24, "123456", Role.EMPLOYEE, true),
                new Account(25, "123456", Role.EMPLOYEE, true),
                new Account(26, "123456", Role.EMPLOYEE, true),
                new Account(27, "123456", Role.EMPLOYEE, true),
                new Account(28, "123456", Role.EMPLOYEE, true),
                new Account(29, "123456", Role.EMPLOYEE, true),
                new Account(30, "123456", Role.EMPLOYEE, true)
        );

        accountRepository.saveAll(accounts);

        List<Employee> employees = Arrays.asList(
                new Employee(1, "Nguyễn Quang Huy", true, DateUtil.parseDate("1990-01-01"), "012345678", "123 Đường Láng, Hà Nội", "0909123456", "nguyenquanghuy@example.com", accounts.get(0)),
                new Employee(2, "Trần Thị Lan", false, DateUtil.parseDate("1988-02-02"), "876543210", "456 Đường Nguyễn Trãi, Hồ Chí Minh", "0912345678", "tranthilan@example.com", accounts.get(1)),
                new Employee(3, "Lê Thanh Bình", true, DateUtil.parseDate("1992-03-03"), "234567890", "789 Đường Lê Duẩn, Đà Nẵng", "0923456789", "lethanhbinh@example.com", accounts.get(2)),
                new Employee(4, "Phạm Minh Tuấn", true, DateUtil.parseDate("1985-04-04"), "345678901", "012 Đường Điện Biên Phủ, Hải Phòng", "0934567890", "phaminhtuan@example.com", accounts.get(3)),
                new Employee(5, "Hoàng Ngọc Anh", false, DateUtil.parseDate("1993-05-05"), "456789012", "345 Đường Cách Mạng Tháng 8, Cần Thơ", "0945678901", "hoangngocanh@example.com", accounts.get(4)),
                new Employee(6, "Đỗ Hải Yến", false, DateUtil.parseDate("1987-06-06"), "567890123", "678 Đường Trần Phú, Nha Trang", "0956789012", "dohaisyen@example.com", accounts.get(5)),
                new Employee(7, "Vũ Đức Thắng", true, DateUtil.parseDate("1994-07-07"), "678901234", "901 Đường Hùng Vương, Huế", "0967890123", "vuducthang@example.com", accounts.get(6)),
                new Employee(8, "Ngô Phương Mai", false, DateUtil.parseDate("1989-08-08"), "789012345", "234 Đường Lê Lợi, Vũng Tàu", "0978901234", "ngophuongmai@example.com", accounts.get(7)),
                new Employee(9, "Bùi Gia Bảo", true, DateUtil.parseDate("1991-09-09"), "890123456", "567 Đường Phạm Văn Đồng, Biên Hòa", "0989012345", "buigiabao@example.com", accounts.get(8)),
                new Employee(10, "Phan Khánh Linh", false, DateUtil.parseDate("1986-10-10"), "901234567", "890 Đường Nguyễn Huệ, Buôn Ma Thuột", "0990123456", "phankhanhlinh@example.com", accounts.get(9))
        );

        employeeRepository.saveAll(employees);
    }

    /* Bill */
    public void seedBills() {

    }

    public void seedTableHistories() {

    }

    /* Feedback */
    public void seedFeedbacks() {

    }

    /* Menu */
    public void seedMenuItemCategories() {
        var menuItemCategories = Arrays.asList(
                new MenuItemCategory(1, "Khai vị & Ăn kèm", null),
                new MenuItemCategory(2, "Thịt", null),
                new MenuItemCategory(3, "Cơm & Canh & Mỳ", null),
                new MenuItemCategory(4, "Lẩu", null),
                new MenuItemCategory(5, "Hải sản", null),
                new MenuItemCategory(6, "Tráng miệng", null)
        );

        if (menuItemCategoryRepository.count() == 0) {
            menuItemCategoryRepository.saveAll(menuItemCategories);
        }

        var menuItems = Arrays.asList(
                new MenuItem(1, "Salad Cá hồi", menuItemCategories.get(0), MenuItemGroup.FOOD, null, null, null, true),
                new MenuItem(2, "Salad mùa xuân", menuItemCategories.get(0), MenuItemGroup.FOOD, null, null, null, true),
                new MenuItem(3, "Há cảo truyền thống Hàn Quốc", menuItemCategories.get(0), MenuItemGroup.FOOD, null, null, null, true),
                new MenuItem(4, "Set kimbap (ALC)", menuItemCategories.get(0), MenuItemGroup.FOOD, null, null, null, true),
                new MenuItem(5, "Toboki xào hải sản", menuItemCategories.get(0), MenuItemGroup.FOOD, null, null, null, true),
                new MenuItem(6, "Bánh hải sản", menuItemCategories.get(0), MenuItemGroup.FOOD, null, null, null, true),
                new MenuItem(7, "Nấm nướng", menuItemCategories.get(0), MenuItemGroup.FOOD, null, null, null, true),
                new MenuItem(8, "Salad cá ngừ (ALC)", menuItemCategories.get(0), MenuItemGroup.FOOD, null, null, null, true),
                new MenuItem(9, "Salad hoa quả", menuItemCategories.get(0), MenuItemGroup.FOOD, null, null, null, true),
                new MenuItem(10, "Salad hành paro", menuItemCategories.get(0), MenuItemGroup.FOOD, null, null, null, true),
                new MenuItem(11, "Dê quân cờ", menuItemCategories.get(1), MenuItemGroup.FOOD, null, null, null, true),
                new MenuItem(12, "Dê nướng tảng", menuItemCategories.get(1), MenuItemGroup.FOOD, null, null, null, true),
                new MenuItem(13, "Diềm bụng bò", menuItemCategories.get(1), MenuItemGroup.FOOD, null, null, null, true),
                new MenuItem(14, "Sườn non bò", menuItemCategories.get(1), MenuItemGroup.FOOD, null, null, null, true),
                new MenuItem(15, "Ba chỉ bò", menuItemCategories.get(1), MenuItemGroup.FOOD, null, null, null, true),
                new MenuItem(16, "Gầu bò", menuItemCategories.get(1), MenuItemGroup.FOOD, null, null, null, true),
                new MenuItem(17, "Sườn bò", menuItemCategories.get(1), MenuItemGroup.FOOD, null, null, null, true),
                new MenuItem(18, "Thăn bò", menuItemCategories.get(1), MenuItemGroup.FOOD, null, null, null, true),
                new MenuItem(19, "Thăn lưng bò", menuItemCategories.get(1), MenuItemGroup.FOOD, null, null, null, true),
                new MenuItem(20, "Sườn heo", menuItemCategories.get(1), MenuItemGroup.FOOD, null, null, null, true),
                new MenuItem(21, "Má heo", menuItemCategories.get(1), MenuItemGroup.FOOD, null, null, null, true),
                new MenuItem(22, "Nạc vai heo", menuItemCategories.get(1), MenuItemGroup.FOOD, null, null, null, true),
                new MenuItem(22, "Ba chi heo", menuItemCategories.get(1), MenuItemGroup.FOOD, null, null, null, true),
                new MenuItem(23, "Mỳ tương đen", menuItemCategories.get(2), MenuItemGroup.FOOD, null, null, null, true),
                new MenuItem(24, "Mỳ bò Hàn Quốc", menuItemCategories.get(2), MenuItemGroup.FOOD, null, null, null, true),
                new MenuItem(25, "Miến xào", menuItemCategories.get(2), MenuItemGroup.FOOD, null, null, null, true),
                new MenuItem(26, "Cơm rang kim chi", menuItemCategories.get(2), MenuItemGroup.FOOD, null, null, null, true),
                new MenuItem(27, "Canh lòng bò", menuItemCategories.get(2), MenuItemGroup.FOOD, null, null, null, true),
                new MenuItem(28, "Canh Naechang Chigae", menuItemCategories.get(2), MenuItemGroup.FOOD, null, null, null, true),
                new MenuItem(29, "Canh rong biển thịt", menuItemCategories.get(2), MenuItemGroup.FOOD, null, null, null, true),
                new MenuItem(30, "Canh kim chi", menuItemCategories.get(2), MenuItemGroup.FOOD, null, null, null, true),
                new MenuItem(31, "Cơm Hàn Quốc", menuItemCategories.get(2), MenuItemGroup.FOOD, null, null, null, true),
                new MenuItem(32, "Cơm bát đá nóng", menuItemCategories.get(2), MenuItemGroup.FOOD, null, null, null, true),
                new MenuItem(34, "Lẩu dê", menuItemCategories.get(3), MenuItemGroup.FOOD, null, null, null, true),
                new MenuItem(35, "Rau lẩu", menuItemCategories.get(3), MenuItemGroup.FOOD, null, null, null, true),
                new MenuItem(36, "Lẩu kim chi", menuItemCategories.get(3), MenuItemGroup.FOOD, null, null, null, true),
                new MenuItem(37, "Lẩu bull", menuItemCategories.get(3), MenuItemGroup.FOOD, null, null, null, true),
                new MenuItem(38, "Miến Hàn Quốc", menuItemCategories.get(3), MenuItemGroup.FOOD, null, null, null, true),
                new MenuItem(39, "Tôm nướng", menuItemCategories.get(4), MenuItemGroup.FOOD, null, null, null, true),
                new MenuItem(40, "Cá hồi", menuItemCategories.get(4), MenuItemGroup.FOOD, null, null, null, true),
                new MenuItem(41, "Cá chình Hàn Quốc", menuItemCategories.get(4), MenuItemGroup.FOOD, null, null, null, true),
                new MenuItem(42, "Bào ngư", menuItemCategories.get(4), MenuItemGroup.FOOD, null, null, null, true),
                new MenuItem(43, "Cá mút đá", menuItemCategories.get(4), MenuItemGroup.FOOD, null, null, null, true),
                new MenuItem(44, "Kem Caramen Flan cake", menuItemCategories.get(5), MenuItemGroup.FOOD, null, null, null, true),
                new MenuItem(45, "Mochi socola", menuItemCategories.get(5), MenuItemGroup.FOOD, null, null, null, true),
                new MenuItem(46, "Mochi trà xanh", menuItemCategories.get(5), MenuItemGroup.FOOD, null, null, null, true),
                new MenuItem(47, "Kem tươi vị sữa chua", menuItemCategories.get(5), MenuItemGroup.FOOD, null, null, null, true),
                new MenuItem(48, "Kem tươi vị Kiwi", menuItemCategories.get(5), MenuItemGroup.FOOD, null, null, null, true),
                new MenuItem(49, "Kem tươi vị Berries", menuItemCategories.get(5), MenuItemGroup.FOOD, null, null, null, true),
                new MenuItem(50, "Kem tươi vị Yuzu (ALC)", menuItemCategories.get(5), MenuItemGroup.FOOD, null, null, null, true)
        );

        if (menuItemRepository.count() == 0) {
            menuItemRepository.saveAll(menuItems);
        }
    }

    /*Prices*/
    public void seedPrices() {
        // Lấy tất cả DayGroup từ cơ sở dữ liệu
        var dayGroups = dayGroupRepository.findAll();

        if (dayGroups.isEmpty()) {
            // Nếu không có DayGroup, tạo mới và lưu vào cơ sở dữ liệu
            var newDayGroups = Arrays.asList(
                    new DayGroup(1, "Ngày thường", true),
                    new DayGroup(2, "Ngày cuối tuần", true),
                    new DayGroup(3, "Ngày lễ", true)
            );
            dayGroups = dayGroupRepository.saveAll(newDayGroups);
        }

        // Tạo DayGroupApplication
        if (dayGroupApplicationRepository.count() == 0) {
            var dayGroupApplications = Arrays.asList(
                    new DayGroupApplication(1, dayGroups.get(0), new Date(), 1),
                    new DayGroupApplication(2, dayGroups.get(0), new Date(), 2),
                    new DayGroupApplication(3, dayGroups.get(0), new Date(), 3),
                    new DayGroupApplication(4, dayGroups.get(0), new Date(), 4),
                    new DayGroupApplication(5, dayGroups.get(0), new Date(), 5),
                    new DayGroupApplication(6, dayGroups.get(1), new Date(), 6),
                    new DayGroupApplication(7, dayGroups.get(1), new Date(), 7)
            );
            dayGroupApplicationRepository.saveAll(dayGroupApplications);
        }

        // Tạo Price
        if (priceRepository.count() == 0) {
            var prices = Arrays.asList(
                    new Price(dayGroups.get(0), new BigDecimal("300000"), new BigDecimal("200000")),
                    new Price(dayGroups.get(1), new BigDecimal("350000"), new BigDecimal("250000")),
                    new Price(dayGroups.get(2), new BigDecimal("500000"), new BigDecimal("300000"))
            );
            priceRepository.saveAll(prices);
        }
    }


    /* Reservation */

    /* Table */
    public void seedTables() {
        var tableGroups = Arrays.asList(
                new TableGroup(1, "Bàn 1 - 2 người", 1, 2),
                new TableGroup(2, "Bàn 3 - 4 người", 3, 4),
                new TableGroup(3, "Bàn 5 - 6 người", 5, 6),
                new TableGroup(4, "Bàn 7 - 10 người", 7, 10)
        );

        if (tableGroupRepository.count() == 0) {
            tableGroupRepository.saveAll(tableGroups);
        }

        var buffetTables = Arrays.asList(
                new BuffetTable(1, "Bàn số 1", tableGroups.get(0)),
                new BuffetTable(2, "Bàn số 2", tableGroups.get(0)),
                new BuffetTable(3, "Bàn số 3", tableGroups.get(0)),
                new BuffetTable(4, "Bàn số 4", tableGroups.get(0)),
                new BuffetTable(5, "Bàn số 5", tableGroups.get(0)),
                new BuffetTable(6, "Bàn số 6", tableGroups.get(0)),
                new BuffetTable(7, "Bàn số 7", tableGroups.get(0)),
                new BuffetTable(8, "Bàn số 8", tableGroups.get(1)),
                new BuffetTable(9, "Bàn số 9", tableGroups.get(1)),
                new BuffetTable(10, "Bàn số 10", tableGroups.get(1)),
                new BuffetTable(11, "Bàn số 11", tableGroups.get(1)),
                new BuffetTable(12, "Bàn số 12", tableGroups.get(2)),
                new BuffetTable(13, "Bàn số 13", tableGroups.get(2)),
                new BuffetTable(14, "Bàn số 14", tableGroups.get(2)),
                new BuffetTable(15, "Bàn số 15", tableGroups.get(3)),
                new BuffetTable(16, "Bàn số 16", tableGroups.get(3))
        );

        if (buffetTableRepository.count() == 0) {
            buffetTableRepository.saveAll(buffetTables);
        }
    }

}
