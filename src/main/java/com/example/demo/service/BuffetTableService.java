package com.example.demo.service;

import com.example.demo.repository.IBuffetTableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BuffetTableService {
    private IBuffetTableRepository buffetTableRepository;

    @Autowired
    public BuffetTableService(IBuffetTableRepository buffetTableRepository) {
        this.buffetTableRepository = buffetTableRepository;
    }
}
