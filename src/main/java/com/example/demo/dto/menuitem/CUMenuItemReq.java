package com.example.demo.dto.menuitem;

import com.example.demo.converter.MenuItemGroupConverter;
import com.example.demo.model.menu.MenuItemCategory;
import com.example.demo.model.menu.MenuItemGroup;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
public class CUMenuItemReq implements Serializable {
    private int menuItemId;
    private String menuItemName;
    private Integer menuItemCategoryId;
    private String menuItemGroup;
    private String description;
    private boolean isActive;

}
