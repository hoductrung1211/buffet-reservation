package com.example.demo.service;

import com.example.demo.model.auth.Customer;
import com.example.demo.repository.ICustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

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

    public Iterable<Customer> findByPhone(String phone, PageRequest pageRequest) {
        return customerRepository.findByPhone(phone, pageRequest);
    }

    public Customer findById(int customerId) {
        return customerRepository.findByCustomerId(customerId);
    }
}
