package com.example.demo.dto.menuitem;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class MenuItemSortView {
    private int menuItemId;
    private String menuItemName;
    private MenuItemCategoryView menuItemCategory;
    private String description;
    private String imageUrl;
    private boolean isActive;
}
