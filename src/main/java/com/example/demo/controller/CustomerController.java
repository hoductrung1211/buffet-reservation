package com.example.demo.controller;

import com.example.demo.dto.account.ChangePassRequest;
import com.example.demo.model.auth.Customer;
import com.example.demo.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

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
            customers = customerService.getCustomersByPhone(phone, pageable);
        } else {
            customers = customerService.getAllCustomers(pageable);
        }

        return ResponseEntity.ok(customers);
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<Optional<Customer>> findById(@PathVariable int customerId) {
        var customer = customerService.getCustomerById(customerId);
        if (customer.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(customer);
    }

}
