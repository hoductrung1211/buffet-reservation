package com.example.demo.controller;

import com.example.demo.service.DayGroupApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/day-group-applications")
public class DayGroupApplicationController {
    private final DayGroupApplicationService dayGroupApplicationService;

    @Autowired
    public DayGroupApplicationController(DayGroupApplicationService dayGroupApplicationService) {
        this.dayGroupApplicationService = dayGroupApplicationService;
    }
}
