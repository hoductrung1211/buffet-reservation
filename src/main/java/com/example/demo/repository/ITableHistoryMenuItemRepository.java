package com.example.demo.repository;

import com.example.demo.model.bill.TableHistoryMenuItem;
import com.example.demo.model.bill.TableHistoryMenuItemId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ITableHistoryMenuItemRepository extends JpaRepository<TableHistoryMenuItem, TableHistoryMenuItemId> {
}
