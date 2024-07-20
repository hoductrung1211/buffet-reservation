package com.example.demo.service;

import com.example.demo.repository.IPriceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PriceService {
    private IPriceRepository priceRepository;

    @Autowired
    public PriceService(IPriceRepository priceRepository) {
        this.priceRepository = priceRepository;
    }
}
