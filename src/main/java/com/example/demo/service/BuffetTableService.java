package com.example.demo.service;

import com.example.demo.model.table.BuffetTable;
import com.example.demo.repository.IBuffetTableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class BuffetTableService {
    private final IBuffetTableRepository buffetTableRepository;

    @Autowired
    public BuffetTableService(IBuffetTableRepository buffetTableRepository) {
        this.buffetTableRepository = buffetTableRepository;
    }

    public List<BuffetTable> getAllBuffetTables() {
        return buffetTableRepository.findAll();
    }

    public Optional<BuffetTable> getBuffetTableById(int buffetTableId) {
        return buffetTableRepository.findById(buffetTableId);
    }

    public BuffetTable createBuffetTable(BuffetTable buffetTable) {
        return buffetTableRepository.save(buffetTable);
    }

    public BuffetTable updateBuffetTable(int buffetTableId, BuffetTable buffetTableDetails) {
        BuffetTable buffetTable = buffetTableRepository.findById(buffetTableId)
                .orElseThrow(() -> new NoSuchElementException("No such buffet table with ID " + buffetTableId));
        buffetTable.setBuffetTableName(buffetTableDetails.getBuffetTableName());
        return buffetTableRepository.save(buffetTable);
    }

    public void deleteBuffetTable(int buffetTableId) {
        BuffetTable buffetTable = buffetTableRepository.findById(buffetTableId)
                .orElseThrow(() -> new NoSuchElementException("No such buffet table with ID " + buffetTableId));
        buffetTableRepository.delete(buffetTable);
    }
}

