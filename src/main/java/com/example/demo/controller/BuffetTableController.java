package com.example.demo.controller;

import com.example.demo.service.BuffetTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/buffet-tables")
public class BuffetTableController {
    private final BuffetTableService buffetTableService;

    @Autowired
    public BuffetTableController(BuffetTableService buffetTableService) {
        this.buffetTableService = buffetTableService;
    }
}
