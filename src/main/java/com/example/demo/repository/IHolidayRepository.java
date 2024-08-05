package com.example.demo.repository;

import com.example.demo.model.price.Holiday;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IHolidayRepository extends JpaRepository<Holiday,Integer> {
    @Query("SELECT CASE WHEN COUNT(h) > 0 THEN " +
            "CASE WHEN (h.year IS NULL OR h.year = :year) THEN TRUE ELSE FALSE END " +
            "ELSE FALSE END " +
            "FROM Holiday h WHERE h.date = :date AND h.month = :month")
    Boolean isHoliday1(@Param("date") Integer date, @Param("month") Integer month, @Param("year") Integer year);

    @Query("SELECT h FROM Holiday h WHERE h.date = :date AND h.month = :month AND (h.year is null OR h.year = :year)")
    Holiday findByDateAndMonth(@Param("date") int date,
                               @Param("month") int month,
                               @Param("year") int year);
}
