package com.example.demo.controller;

import com.example.demo.dto.CreateTableGroupReq;
import com.example.demo.model.table.TableGroup;
import com.example.demo.service.TableGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/table-groups")
public class TableGroupController {
    private final TableGroupService tableGroupService;

    @Autowired
    public TableGroupController(TableGroupService tableGroupService) {
        this.tableGroupService = tableGroupService;
    }

    @GetMapping
    public ResponseEntity<Iterable<TableGroup>> getAllTableGroups(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        var pageable = PageRequest.of(page, size);
        var tableGroups = tableGroupService.getAllTableGroups(pageable);

        return ResponseEntity.ok(tableGroups);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TableGroup> getTableGroupById(@PathVariable int id) {
        var tableGroup = tableGroupService.getTableGroupById(id);
        return tableGroup.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<TableGroup> createTableGroup(@RequestBody CreateTableGroupReq req) {
        try {
            var createdTableGroup = tableGroupService.createTableGroup(
                    TableGroup
                            .builder()
                            .tableGroupName(req.getTableGroupName())
                            .minPeopleQuantity(req.getMinPeopleQuantity())
                            .maxPeopleQuantity(req.getMaxPeopleQuantity())
                            .build()
            );
            URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(createdTableGroup.getTableGroupId())
                    .toUri();

            return ResponseEntity.created(location).body(createdTableGroup);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PutMapping(value = "/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<TableGroup> updateTableGroup(
            @PathVariable int id,
            @RequestBody CreateTableGroupReq req
    ) {
        try {
            new TableGroup();
            var updatedTableGroup = tableGroupService.updateTableGroup(
                    id,
                    TableGroup
                            .builder()
                            .tableGroupName(req.getTableGroupName())
                            .minPeopleQuantity(req.getMinPeopleQuantity())
                            .maxPeopleQuantity(req.getMaxPeopleQuantity())
                            .build()
            );

            return ResponseEntity.ok(tableGroupService.updateTableGroup(id, updatedTableGroup));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTableGroup(@PathVariable int id) {
        try {
            tableGroupService.deleteTableGroup(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
