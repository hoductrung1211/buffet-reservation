package com.example.demo.service;

import com.example.demo.dto.menuitem.MenuItemCategoryView;
import com.example.demo.dto.menuitem.MenuItemSortView;
import com.example.demo.model.menu.MenuItemCategory;
import com.example.demo.repository.IMenuItemCategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MenuItemCategoryService {
    private final IMenuItemCategoryRepository menuItemCategoryRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public MenuItemCategoryService(IMenuItemCategoryRepository menuItemCategoryRepository, ModelMapper modelMapper) {
        this.menuItemCategoryRepository = menuItemCategoryRepository;
        this.modelMapper = modelMapper;
    }

    public ResponseEntity<?> getAll() {
        List<MenuItemCategory> menuItemCategories = menuItemCategoryRepository.findAll();
        return ResponseEntity.ok(menuItemCategories
                .stream()
                .map(menuItemCategory -> modelMapper.map(menuItemCategory, MenuItemCategoryView.class))
                .collect(Collectors.toList())
        );
    }
}
