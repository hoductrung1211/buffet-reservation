package com.example.demo.service;

import com.example.demo.dto.CreateDayGroupReq;
import com.example.demo.dto.UpdateDayGroupReq;
import com.example.demo.model.price.DayGroup;
import com.example.demo.repository.IDayGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class DayGroupService {
    private final IDayGroupRepository dayGroupRepository;

    @Autowired
    public DayGroupService(IDayGroupRepository dayGroupRepository, TableGroupService tableGroupService) {
        this.dayGroupRepository = dayGroupRepository;
    }

    public Iterable<DayGroup> getAllDayGroups(PageRequest pageRequest) {
        return dayGroupRepository.findAll(pageRequest);
    }

    public Optional<DayGroup> getDayGroupById(int dayGroupId) {
        return dayGroupRepository.findById(dayGroupId);
    }

    public DayGroup createDayGroup(CreateDayGroupReq createDayGroup) {
        return dayGroupRepository.save(
                DayGroup
                        .builder()
                        .dayGroupName(createDayGroup.getDayGroupName())
                        .build()
        );
    }

    public Optional<DayGroup> updateDayGroup(int dayGroupId, UpdateDayGroupReq updateDayGroup) {
        return Optional.ofNullable(dayGroupRepository.findById(dayGroupId)
                .map(dayGroup -> {
                    dayGroup.setDayGroupName(updateDayGroup.getDayGroupName());
                    dayGroup.setActive(updateDayGroup.isActive());

                    return dayGroupRepository.save(dayGroup);
                })
                .orElseThrow(() -> new NoSuchElementException("No such day group with ID: " + dayGroupId))
        );
    }

    public void deleteDayGroup(int dayGroupId) {
        DayGroup dayGroup = dayGroupRepository.findById(dayGroupId)
                .orElseThrow(() -> new NoSuchElementException("No such day group with ID: " + dayGroupId));
        dayGroupRepository.delete(dayGroup);
    }
}
