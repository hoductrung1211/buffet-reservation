package com.example.demo.repository;

import com.example.demo.model.menu.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IMenuItemRepository extends JpaRepository<MenuItem, Integer> {
}
