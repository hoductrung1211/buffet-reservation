package com.example.demo.repository;

import com.example.demo.model.auth.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICustomerRepository extends JpaRepository<Customer, Integer> {
    Page<Customer> findAll(Pageable pageable);
    Page<Customer> findByPhone(String phone, Pageable pageable);
    Customer findByCustomerId(int customerId);
}
