package com.example.demo.controller;

import com.example.demo.model.table.BuffetTable;
import com.example.demo.service.BuffetTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/buffet-tables")
public class BuffetTableController {
    private final BuffetTableService buffetTableService;

    @Autowired
    public BuffetTableController(BuffetTableService buffetTableService) {
        this.buffetTableService = buffetTableService;
    }

    @GetMapping
    public List<BuffetTable> getAllBuffetTables() {
        return buffetTableService.getAllBuffetTables();
    }

    @GetMapping("/{buffetTableId}")
    public ResponseEntity<BuffetTable> getDayGroupById(@PathVariable(value = "buffetTableId") int buffetTableId) {
        Optional<BuffetTable> buffetTable = buffetTableService.getBuffetTableById(buffetTableId);
        return buffetTable.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public BuffetTable createDayGroup(@RequestBody BuffetTable buffetTable) {
        return buffetTableService.createBuffetTable(buffetTable);
    }

    @PutMapping("/{buffetTableId}")
    public ResponseEntity<BuffetTable> updateDayGroup(@PathVariable(value = "buffetTableId") int buffetTableId, @RequestBody BuffetTable buffetTableDetails) {
        try {
            BuffetTable updateBuffetTable  = buffetTableService.updateBuffetTable(buffetTableId, buffetTableDetails);
            return ResponseEntity.ok(updateBuffetTable);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{buffetTableId}")
    public ResponseEntity<Void> deleteBuffetTable(@PathVariable(value = "buffetTableId") int buffetTableId) {
        try {
            buffetTableService.deleteBuffetTable(buffetTableId);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
