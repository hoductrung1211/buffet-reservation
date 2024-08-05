package com.example.demo.repository;

import com.example.demo.model.table.BuffetTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IBuffetTableRepository extends JpaRepository<BuffetTable, Integer> {
    @Query("SELECT bt FROM BuffetTable bt WHERE bt.tableGroup.minPeopleQuantity <= :quantity " +
            "AND bt.tableGroup.maxPeopleQuantity <= :quantity+2")
    List<BuffetTable> findAllByQuantity(@Param("quantity") Integer quantity);
}
