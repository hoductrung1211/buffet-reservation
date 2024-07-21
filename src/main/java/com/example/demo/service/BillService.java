package com.example.demo.service;

import com.example.demo.repository.IBillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BillService {
    private final IBillRepository billRepository;

    @Autowired
    public BillService(IBillRepository billRepository) {
        this.billRepository = billRepository;
    }
}
