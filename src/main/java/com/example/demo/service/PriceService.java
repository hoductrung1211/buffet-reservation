package com.example.demo.service;

import com.example.demo.model.price.Price;
import com.example.demo.repository.IPriceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PriceService {
    private final IPriceRepository priceRepository;

    @Autowired
    public PriceService(IPriceRepository priceRepository) {
        this.priceRepository = priceRepository;
    }

    public List<Price> getAllPrices() {
        return priceRepository.findAll();
    }

    public Optional<Price> getPriceById(int priceId) {
        return priceRepository.findById(priceId);
    }

    public Price createPrice(Price price) {
        return priceRepository.save(price);
    }

    public Price updatePrice(int priceId, Price priceDetails) {
        Price price = priceRepository.findById(priceId).orElseThrow(() -> new RuntimeException("Price not found"));
        price.setDayGroup(priceDetails.getDayGroup());
        price.setAdultPrice(priceDetails.getAdultPrice());
        price.setChildPrice(priceDetails.getChildPrice());
        return priceRepository.save(price);
    }

    public void deletePrice(int priceId) {
        Price price = priceRepository.findById(priceId).orElseThrow(() -> new RuntimeException("Price not found"));
        priceRepository.delete(price);
    }
}
