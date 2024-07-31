package com.example.demo.service;

import com.example.demo.dto.account.ChangePassRequest;
import com.example.demo.model.auth.Customer;
import com.example.demo.repository.ICustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerService {
    private final ICustomerRepository customerRepository;

    @Autowired
    public CustomerService(ICustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Iterable<Customer> getAllCustomers(PageRequest pageRequest) {
        return customerRepository.findAll(pageRequest);
    }

    public Iterable<Customer> getCustomersByPhone(String phone, PageRequest pageRequest) {
        return customerRepository.getByPhone(phone, pageRequest);
    }

    public Optional<Customer> getCustomerById(int customerId) {
        return customerRepository.findById(customerId);
    }

}
