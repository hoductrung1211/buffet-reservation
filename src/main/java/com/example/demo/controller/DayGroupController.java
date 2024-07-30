package com.example.demo.controller;

import com.example.demo.dto.CreateDayGroupReq;
import com.example.demo.model.price.DayGroup;
import com.example.demo.service.DayGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/day-groups")
public class DayGroupController {
    private final DayGroupService dayGroupService;

    @Autowired
    public DayGroupController(DayGroupService dayGroupService) {
        this.dayGroupService = dayGroupService;
    }

    @GetMapping
    public ResponseEntity<Iterable<DayGroup>> getAllDayGroups(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        var pageable = PageRequest.of(page,size);
        var dayGroups = dayGroupService.getAllDayGroups(pageable);

        return ResponseEntity.ok(dayGroups);
    }

    @GetMapping("/{dayGroupId}")
    public ResponseEntity<DayGroup> getDayGroupById(@PathVariable(value = "dayGroupId") int dayGroupId) {
        return dayGroupService.getDayGroupById(dayGroupId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<DayGroup> createDayGroup(@RequestBody CreateDayGroupReq req) {
        try {
            var createdDayGroup = dayGroupService.createDayGroup(req);
            URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(createdDayGroup.getDayGroupById())
                    .toUri();

            return ResponseEntity.created(location).body(createdDayGroup);
        } catch (Exception ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{dayGroupId}")
    public ResponseEntity<DayGroup> updateDayGroup(@PathVariable(value = "dayGroupId") int dayGroupId, @RequestBody DayGroup dayGroupDetails) {
        try {
            DayGroup updatedDayGroup = dayGroupService.updateDayGroup(dayGroupId, dayGroupDetails);
            return ResponseEntity.ok(updatedDayGroup);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{dayGroupId}")
    public ResponseEntity<Void> deleteDayGroup(@PathVariable(value = "dayGroupId") int dayGroupId) {
        try {
            dayGroupService.deleteDayGroup(dayGroupId);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
