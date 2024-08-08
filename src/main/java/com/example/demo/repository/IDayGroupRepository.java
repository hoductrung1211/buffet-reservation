package com.example.demo.repository;

import com.example.demo.model.price.DayGroup;
import com.example.demo.model.price.DayGroupName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IDayGroupRepository extends JpaRepository<DayGroup, Integer> {
    @Query("SELECT dg FROM DayGroup dg WHERE dg.dayGroupName = :name AND dg.isActive is true ")
    DayGroup findByDayGroupNameAndTrue(@Param("name") DayGroupName name);
}
