package com.example.demo.repository;

import com.example.demo.model.price.Price;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface IPriceRepository extends JpaRepository<Price, Integer> {
    List<Price> findAllByDayGroup_DateGroupId(Integer id);

    @Query("SELECT p FROM Price p " +
            "WHERE p.applicationDate = (SELECT MAX(p2.applicationDate) " +
            "FROM Price p2 " +
            "WHERE p2.applicationDate <= :date " +
            "AND p2.dayGroup = p.dayGroup)")
    List<Price> findPricesByDate(@Param("date") LocalDate date);
    @Query("SELECT p FROM Price p " +
            "WHERE p.applicationDate = (SELECT MAX(p2.applicationDate) " +
            "FROM Price p2 " +
            "WHERE p2.applicationDate <= :date " +
            "AND p2.dayGroup.dateGroupId = :dateGroupId)")
    Price findByDateGroupId(@Param("date") LocalDate date,
                            @Param("dateGroupId") Integer dateGroupId);

}
