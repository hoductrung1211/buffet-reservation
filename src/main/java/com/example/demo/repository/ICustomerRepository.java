package com.example.demo.repository;

import com.example.demo.model.auth.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ICustomerRepository extends JpaRepository<Customer, Integer> {
    Page<Customer> getByPhone(String phone, Pageable pageable);
    Customer findCustomerByPhone(String phone);

    Optional<Customer> existsByPhone(String phone);
}
