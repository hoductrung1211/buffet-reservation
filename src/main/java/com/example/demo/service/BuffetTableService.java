package com.example.demo.service;

import com.example.demo.dto.CreateBuffetTableReq;
import com.example.demo.model.table.BuffetTable;
import com.example.demo.repository.IBuffetTableRepository;
import com.example.demo.repository.ITableGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class BuffetTableService {
    private final IBuffetTableRepository buffetTableRepository;
    private final ITableGroupRepository tableGroupRepository;

    @Autowired
    public BuffetTableService(IBuffetTableRepository buffetTableRepository, ITableGroupRepository tableGroupRepository) {
        this.buffetTableRepository = buffetTableRepository;
        this.tableGroupRepository = tableGroupRepository;
    }

    public Iterable<BuffetTable> getAllBuffetTables(PageRequest pageRequest) {
        return buffetTableRepository.findAll(pageRequest);
    }

    public Optional<BuffetTable> getBuffetTableById(int buffetTableId) {
        return buffetTableRepository.findById(buffetTableId);
    }

    public BuffetTable createBuffetTable(CreateBuffetTableReq createBuffetTable) {
        var tableGroup = tableGroupRepository.findById(createBuffetTable.getTableGroupId())
                .orElseThrow(() -> new NoSuchElementException("Table group not found"));

        return buffetTableRepository.save(
            BuffetTable
                    .builder()
                    .buffetTableName(createBuffetTable.getBuffetTableName())
                    .tableGroup(tableGroup)
                    .build()
        );
    }

    public Optional<BuffetTable> updateBuffetTable(int buffetTableId, CreateBuffetTableReq updateBuffetTable) {
        return Optional.ofNullable(buffetTableRepository.findById(buffetTableId)
                .map(buffetTable -> {
                    var tablegroup = tableGroupRepository.findById(updateBuffetTable.getTableGroupId())
                            .orElseThrow(() -> new NoSuchElementException("Table group not found"));

                    buffetTable.setBuffetTableName(updateBuffetTable.getBuffetTableName());
                    buffetTable.setTableGroup(tablegroup);

                    return buffetTableRepository.save(buffetTable);
                })
                .orElseThrow(() -> new NoSuchElementException("No such buffet table with ID: " + buffetTableId)));
    }

    public void deleteBuffetTable(int buffetTableId) {
        BuffetTable buffetTable = buffetTableRepository.findById(buffetTableId)
                .orElseThrow(() -> new NoSuchElementException("No such buffet table with ID: " + buffetTableId));
        buffetTableRepository.delete(buffetTable);
    }
}

