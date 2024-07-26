package com.example.demo.controller;

import com.example.demo.model.table.TableGroup;
import com.example.demo.service.TableGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/table-groups")
public class TableGroupController {
    private final TableGroupService tableGroupService;

    @Autowired
    public TableGroupController(TableGroupService tableGroupService) {
        this.tableGroupService = tableGroupService;
    }

    @GetMapping
    public ResponseEntity<List<TableGroup>> getAllTableGroups() {
        return new ResponseEntity<>(tableGroupService.getAllTableGroups(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TableGroup> getTableGroupById(@PathVariable int id) {
        Optional<TableGroup> tableGroup = tableGroupService.getTableGroupById(id);
        return tableGroup.map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<TableGroup> createTableGroup(@RequestBody TableGroup tableGroup) {
        try {
            return new ResponseEntity<>(tableGroupService.createTableGroup(tableGroup), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<TableGroup> updateTableGroup(@PathVariable int id, @RequestBody TableGroup tableGroup) {
        try {
            return new ResponseEntity<>(tableGroupService.updateTableGroup(id, tableGroup), HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTableGroup(@PathVariable int id) {
        try {
            tableGroupService.deleteTableGroup(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
