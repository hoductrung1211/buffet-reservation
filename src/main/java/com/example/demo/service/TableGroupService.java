package com.example.demo.service;

import com.example.demo.model.table.TableGroup;
import com.example.demo.repository.ITableGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.Optional;

@Service
public class TableGroupService {
    private final ITableGroupRepository tableGroupRepository;

    @Autowired
    public TableGroupService(ITableGroupRepository tableGroupRepository) {
        this.tableGroupRepository = tableGroupRepository;
    }

    public Iterable<TableGroup> getAllTableGroups(PageRequest pageRequest) {
        return tableGroupRepository.findAll(pageRequest);
    }

    public Optional<TableGroup> getTableGroupById(int tableGroupId) {
        return tableGroupRepository.findById(tableGroupId);
    }

    public TableGroup createTableGroup(TableGroup tableGroup) {
        return tableGroupRepository.save(tableGroup);
    }

    public TableGroup updateTableGroup(int tableGroupId, TableGroup tableGroup) {
        if (tableGroupRepository.existsById(tableGroupId)) {
            tableGroup.setTableGroupId(tableGroupId);
            return tableGroupRepository.save(tableGroup);
        } else {
            throw new RuntimeException("TableGroup not found");
        }
    }

    public void deleteTableGroup(int tableGroupId) {
        if (tableGroupRepository.existsById(tableGroupId)) {
            tableGroupRepository.deleteById(tableGroupId);
        } else {
            throw new RuntimeException("TableGroup not found");
        }
    }
}
