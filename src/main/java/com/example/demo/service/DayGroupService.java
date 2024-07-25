package com.example.demo.service;

import com.example.demo.model.price.DayGroup;
import com.example.demo.repository.IDayGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class DayGroupService {
    private final IDayGroupRepository dayGroupRepository;

    @Autowired
    public DayGroupService(IDayGroupRepository dayGroupRepository) {
        this.dayGroupRepository = dayGroupRepository;
    }

    public List<DayGroup> getAllDayGroups() {
        return dayGroupRepository.findAll();
    }

    public Optional<DayGroup> getDayGroupById(int dayGroupId) {
        return dayGroupRepository.findById(dayGroupId);
    }

    public DayGroup createDayGroup(DayGroup dayGroup) {
        return dayGroupRepository.save(dayGroup);
    }

    public DayGroup updateDayGroup(int dayGroupId, DayGroup dayGroupDetails) {
        DayGroup dayGroup = dayGroupRepository.findById(dayGroupId)
                .orElseThrow(() -> new NoSuchElementException("DayGroup not found for this id :: " + dayGroupId));
        dayGroup.setDayGroupName(dayGroupDetails.getDayGroupName());
        dayGroup.setActive(dayGroupDetails.isActive());
        return dayGroupRepository.save(dayGroup);
    }

    public void deleteDayGroup(int dayGroupId) {
        DayGroup dayGroup = dayGroupRepository.findById(dayGroupId)
                .orElseThrow(() -> new NoSuchElementException("DayGroup not found for this id :: " + dayGroupId));
        dayGroupRepository.delete(dayGroup);
    }
}
