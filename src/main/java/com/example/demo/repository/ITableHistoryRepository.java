package com.example.demo.repository;

import com.example.demo.model.bill.TableHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITableHistoryRepository extends JpaRepository<TableHistory, Integer> {
}
