package com.example.demo.service;

import com.example.demo.repository.IMenuItemCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MenuItemCategoryService {
    private final IMenuItemCategoryRepository menuItemCategoryRepository;

    @Autowired
    public MenuItemCategoryService(IMenuItemCategoryRepository menuItemCategoryRepository) {
        this.menuItemCategoryRepository = menuItemCategoryRepository;
    }
}
