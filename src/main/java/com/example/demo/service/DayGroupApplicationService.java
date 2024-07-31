package com.example.demo.service;

import com.example.demo.model.price.DayGroupApplication;
import com.example.demo.repository.IDayGroupApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DayGroupApplicationService {
    private final IDayGroupApplicationRepository applicationRepository;

    @Autowired
    public DayGroupApplicationService(IDayGroupApplicationRepository applicationRepository) {
        this.applicationRepository = applicationRepository;
    }

    public List<DayGroupApplication> findAll() {
        return applicationRepository.findAll();
    }

    public Optional<DayGroupApplication> findById(int dayGroupApplicationId) {
        return applicationRepository.findById(dayGroupApplicationId);
    }

    public DayGroupApplication save(DayGroupApplication dayGroupApplication) {
        return applicationRepository.save(dayGroupApplication);
    }

    public void deleteById(int dayGroupApplicationId) {
        applicationRepository.deleteById(dayGroupApplicationId);
    }
}
