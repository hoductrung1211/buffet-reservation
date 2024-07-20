package com.example.demo.repository;

import com.example.demo.model.auth.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAccountRepository extends JpaRepository<Account, Integer> {
//    Account findByCustomerPhone(String phone);
//    Account findByEmployeeId(String employeeId);
}
