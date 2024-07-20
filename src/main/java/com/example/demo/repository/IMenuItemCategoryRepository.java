package com.example.demo.repository;

import com.example.demo.model.menu.MenuItemCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IMenuItemCategoryRepository extends JpaRepository<MenuItemCategory, Integer> {
}
