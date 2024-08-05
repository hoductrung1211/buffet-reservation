package com.example.demo.repository;

import com.example.demo.model.table.TableGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ITableGroupRepository extends JpaRepository<TableGroup, Integer> {
    @Query("SELECT tg FROM TableGroup tg WHERE tg.minPeopleQuantity <= :quantity AND tg.maxPeopleQuantity >= :quantity")
    List<TableGroup> findAllByQuantity(@Param("quantity") Integer quantity);
}
