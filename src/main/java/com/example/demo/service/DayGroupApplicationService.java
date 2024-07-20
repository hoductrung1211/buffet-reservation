package com.example.demo.service;

import com.example.demo.repository.IDayGroupApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DayGroupApplicationService {
    private IDayGroupApplicationRepository applicationRepository;

    @Autowired
    public DayGroupApplicationService(IDayGroupApplicationRepository applicationRepository) {
        this.applicationRepository = applicationRepository;
    }
}
