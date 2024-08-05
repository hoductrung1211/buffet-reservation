package com.example.demo.dto.menuitem;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MenuItemCategoryView {
    private int menuItemCategoryId;
    private String menuItemCategoryName;
}
