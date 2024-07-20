package com.example.demo.converter;

import com.example.demo.model.menu.MenuItemGroup;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class MenuItemGroupConverter implements AttributeConverter<MenuItemGroup, Integer> {

    @Override
    public Integer convertToDatabaseColumn(MenuItemGroup menuItemGroup) {
        return menuItemGroup != null ? menuItemGroup.getValue() : 0;
    }

    @Override
    public MenuItemGroup convertToEntityAttribute(Integer value) {
        return value != null ? MenuItemGroup.getFromValue(value) : null;
    }
}
