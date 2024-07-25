package com.example.demo.controller;

import com.example.demo.model.price.Price;
import com.example.demo.service.PriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/prices")
public class PriceController {
    private final PriceService priceService;

    @Autowired
    public PriceController(PriceService priceService) {
        this.priceService = priceService;
    }

    @GetMapping
    public List<Price> getAllPrices() {
        return priceService.getAllPrices();
    }

    @GetMapping("/{priceId}")
    public ResponseEntity<Price> getPriceById(@PathVariable int priceId) {
        return priceService.getPriceById(priceId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Price createPrice(@RequestBody Price price) {
        return priceService.createPrice(price);
    }

    @PutMapping("/{priceId}")
    public ResponseEntity<Price> updatePrice(@PathVariable int priceId, @RequestBody Price priceDetails) {
        return ResponseEntity.ok(priceService.updatePrice(priceId, priceDetails));
    }

    @DeleteMapping("/{priceId}")
    public ResponseEntity<Void> deletePrice(@PathVariable int priceId) {
        priceService.deletePrice(priceId);
        return ResponseEntity.noContent().build();
    }
}
