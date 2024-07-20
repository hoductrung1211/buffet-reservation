package com.example.demo.controller;

import com.example.demo.service.TableGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/table-groups")
public class TableGroupController {
    private final TableGroupService tableGroupService;

    @Autowired
    public TableGroupController(TableGroupService tableGroupService) {
        this.tableGroupService = tableGroupService;
    }
}
