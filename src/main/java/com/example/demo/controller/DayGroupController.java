package com.example.demo.controller;

import com.example.demo.service.DayGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/day-groups")
public class DayGroupController {
    private final DayGroupService dayGroupService;

    @Autowired
    public DayGroupController(DayGroupService dayGroupService) {
        this.dayGroupService = dayGroupService;
    }
}
