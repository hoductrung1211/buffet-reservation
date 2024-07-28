package com.example.demo.controller;

import com.example.demo.dto.CreateBuffetTableReq;
import com.example.demo.model.table.BuffetTable;
import com.example.demo.service.BuffetTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
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
    public ResponseEntity<Iterable<BuffetTable>> getAllBuffetTables(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        var pageable = PageRequest.of(page, size);
        var buffetTables = buffetTableService.getAllBuffetTables(pageable);

        return ResponseEntity.ok(buffetTables);
    }

    @GetMapping("/{buffetTableId}")
    public ResponseEntity<BuffetTable> getDayGroupById(@PathVariable int buffetTableId) {
        return buffetTableService.getBuffetTableById(buffetTableId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<BuffetTable> createDayGroup(@RequestBody CreateBuffetTableReq req) {
        try {
            var createdBuffetTable = buffetTableService.createBuffetTable(req);
            URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(createdBuffetTable.getBuffetTableId())
                    .toUri();

            return ResponseEntity.created(location).body(createdBuffetTable);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PutMapping(value = "/{buffetTableId}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<BuffetTable> updateDayGroup(@PathVariable(value = "buffetTableId") int buffetTableId, @RequestBody CreateBuffetTableReq req) {
        return buffetTableService.updateBuffetTable(buffetTableId, req)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @DeleteMapping("/{buffetTableId}")
    public ResponseEntity<Void> deleteBuffetTable(@PathVariable(value = "buffetTableId") int buffetTableId) {
        try {
            buffetTableService.deleteBuffetTable(buffetTableId);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
