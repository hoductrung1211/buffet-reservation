package com.example.demo.service;

import com.example.demo.repository.ITableHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TableHistoryService {
    private ITableHistoryRepository tableHistoryRepository;

    @Autowired
    public TableHistoryService(ITableHistoryRepository tableHistoryRepository) {
        this.tableHistoryRepository = tableHistoryRepository;
    }
}
