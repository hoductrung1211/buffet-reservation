package com.example.demo.controller;

import com.example.demo.model.price.DayGroup;
import com.example.demo.service.DayGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/day-groups")
public class DayGroupController {
    private final DayGroupService dayGroupService;

    @Autowired
    public DayGroupController(DayGroupService dayGroupService) {
        this.dayGroupService = dayGroupService;
    }

    @GetMapping
    public List<DayGroup> getAllDayGroups() {
        return dayGroupService.getAllDayGroups();
    }

    @GetMapping("/{dayGroupId}")
    public ResponseEntity<DayGroup> getDayGroupById(@PathVariable(value = "dayGroupId") int dayGroupId) {
        Optional<DayGroup> dayGroup = dayGroupService.getDayGroupById(dayGroupId);
        return dayGroup.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public DayGroup createDayGroup(@RequestBody DayGroup dayGroup) {
        return dayGroupService.createDayGroup(dayGroup);
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
