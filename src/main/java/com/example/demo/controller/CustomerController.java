package com.example.demo.controller;

import com.example.demo.model.auth.Customer;
import com.example.demo.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {
    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public ResponseEntity<Iterable<Customer>> getAllCustomers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String phone
    ) {
        var pageable = PageRequest.of(page, size);
        Iterable<Customer> customers;

        if (phone != null && !phone.isEmpty()) {
            customers = customerService.findByPhone(phone, pageable);
        } else {
            customers = customerService.getAllCustomers(pageable);
        }

        return ResponseEntity.ok(customers);
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<Customer> findById(@PathVariable int customerId) {
        var customer = customerService.findById(customerId);
        if (customer == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(customer);
    }
}
