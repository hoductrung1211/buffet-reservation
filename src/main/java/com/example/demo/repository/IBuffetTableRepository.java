package com.example.demo.repository;

import com.example.demo.model.table.BuffetTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IBuffetTableRepository extends JpaRepository<BuffetTable, Integer> {
}
