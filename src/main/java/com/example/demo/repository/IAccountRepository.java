package com.example.demo.repository;

import com.example.demo.model.auth.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IAccountRepository extends JpaRepository<Account, Integer> {
//    Account findByCustomerPhone(String phone);
    Optional<Account> findByEmployee_EmployeeId(int employeeId);
    Optional<Account> findByCustomer_Phone(String phone);
}
