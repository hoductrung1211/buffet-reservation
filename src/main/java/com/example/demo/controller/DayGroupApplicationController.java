package com.example.demo.controller;

import com.example.demo.model.price.DayGroupApplication;
import com.example.demo.service.DayGroupApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/day-group-applications")
public class DayGroupApplicationController {
    private final DayGroupApplicationService dayGroupApplicationService;

    @Autowired
    public DayGroupApplicationController(DayGroupApplicationService dayGroupApplicationService) {
        this.dayGroupApplicationService = dayGroupApplicationService;
    }

    @GetMapping
    public List<DayGroupApplication> getAll() {
        return dayGroupApplicationService.findAll();
    }

    @GetMapping("/{dayGroupApplicationId}")
    public ResponseEntity<DayGroupApplication> getById(@PathVariable int dayGroupApplicationId) {
        Optional<DayGroupApplication> dayGroupApplication = dayGroupApplicationService.findById(dayGroupApplicationId);
        return dayGroupApplication.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public DayGroupApplication create(@RequestBody DayGroupApplication dayGroupApplication) {
        return dayGroupApplicationService.save(dayGroupApplication);
    }

    @PutMapping("/{dayGroupApplicationId}")
    public ResponseEntity<DayGroupApplication> update(@PathVariable int dayGroupApplicationId, @RequestBody DayGroupApplication dayGroupApplication) {
        if (!dayGroupApplicationService.findById(dayGroupApplicationId).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        dayGroupApplication.setDateGroupApplicationId(dayGroupApplicationId);
        return ResponseEntity.ok(dayGroupApplicationService.save(dayGroupApplication));
    }

    @DeleteMapping("/{dayGroupApplicationId}")
    public ResponseEntity<Void> delete(@PathVariable int dayGroupApplicationId) {
        if (!dayGroupApplicationService.findById(dayGroupApplicationId).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        dayGroupApplicationService.deleteById(dayGroupApplicationId);
        return ResponseEntity.noContent().build();
    }
}
