package com.example.demo.controller;

import com.example.demo.service.TableHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/table-histories")
public class TableHistoryController {
    private final TableHistoryService tableHistoryService;

    @Autowired
    public TableHistoryController(TableHistoryService tableHistoryService) {
        this.tableHistoryService = tableHistoryService;
    }
}
