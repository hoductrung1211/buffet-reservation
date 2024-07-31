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

    public TableGroup createTableGroup(CreateTableGroupReq createTableGroup) {
        return tableGroupRepository.save(
                TableGroup
                    .builder()
                    .tableGroupName(createTableGroup.getTableGroupName())
                    .minPeopleQuantity(createTableGroup.getMinPeopleQuantity())
                    .maxPeopleQuantity(createTableGroup.getMaxPeopleQuantity())
                    .build()
        );
    }

    public Optional<TableGroup> updateTableGroup(int tableGroupId, CreateTableGroupReq updateTableGroup) {
        return tableGroupRepository.findById(tableGroupId)
                .map(tableGroup -> {
                    tableGroup.setTableGroupName(updateTableGroup.getTableGroupName());
                    tableGroup.setMinPeopleQuantity(updateTableGroup.getMinPeopleQuantity());
                    tableGroup.setMaxPeopleQuantity(updateTableGroup.getMaxPeopleQuantity());

                    return tableGroupRepository.save(tableGroup);
                });
    }

    // TODO: Update this: void ... like BuffetTableService delete method
    public boolean deleteTableGroup(int tableGroupId) {
        if (tableGroupRepository.existsById(tableGroupId)) {
            tableGroupRepository.deleteById(tableGroupId);
            return true;
        }
        return false;
    }
}
