package com.example.demo.service;

import com.example.demo.repository.ITableGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TableGroupService {
    private final ITableGroupRepository tableGroupRepository;

    @Autowired
    public TableGroupService(ITableGroupRepository tableGroupRepository) {
        this.tableGroupRepository = tableGroupRepository;
    }
}
