package com.example.demo.service;

import com.example.demo.repository.IDayGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DayGroupService {
    private final IDayGroupRepository dayGroupRepository;

    @Autowired
    public DayGroupService(IDayGroupRepository dayGroupRepository) {
        this.dayGroupRepository = dayGroupRepository;
    }
}
