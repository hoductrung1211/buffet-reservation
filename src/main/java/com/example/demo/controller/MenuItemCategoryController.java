package com.example.demo.controller;

import com.example.demo.service.MenuItemCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/menu-item-categories")
public class MenuItemCategoryController {
    private final MenuItemCategoryService menuItemCategoryService;

    @Autowired
    public MenuItemCategoryController(MenuItemCategoryService menuItemCategoryService) {
        this.menuItemCategoryService = menuItemCategoryService;
    }

    @GetMapping("")
    public ResponseEntity<?> getAll(){
        return menuItemCategoryService.getAll();
    }

}
