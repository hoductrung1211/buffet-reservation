package com.example.demo.repository;

import com.example.demo.model.table.TableGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITableGroupRepository extends JpaRepository<TableGroup, Integer> {
}
