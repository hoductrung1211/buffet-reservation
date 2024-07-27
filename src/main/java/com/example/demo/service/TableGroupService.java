package com.example.demo.service;

import com.example.demo.dto.CreateTableGroupReq;
import com.example.demo.model.table.TableGroup;
import com.example.demo.repository.ITableGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

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

    public Optional<TableGroup> updateTableGroup(int tableGroupId, CreateTableGroupReq req) {
        return tableGroupRepository.findById(tableGroupId)
                .map(tableGroup -> {
                    tableGroup.setTableGroupName(req.getTableGroupName());
                    tableGroup.setMinPeopleQuantity(req.getMinPeopleQuantity());
                    tableGroup.setMaxPeopleQuantity(req.getMaxPeopleQuantity());

                    return tableGroupRepository.save(tableGroup);
                });
    }

    public boolean deleteTableGroup(int tableGroupId) {
        if (tableGroupRepository.existsById(tableGroupId)) {
            tableGroupRepository.deleteById(tableGroupId);
            return true;
        }
        return false;
    }
}
