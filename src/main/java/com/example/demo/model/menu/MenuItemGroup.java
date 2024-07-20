package com.example.demo.model.menu;

import lombok.Getter;

@Getter
public enum MenuItemGroup {
    FOOD(1),
    BEVERAGE(2);

    private final int value;

    MenuItemGroup(final int value) {
        this.value = value;
    }

    public static MenuItemGroup getFromValue(final int value) {
        for (final MenuItemGroup itemGroup : MenuItemGroup.values()) {
            if (itemGroup.getValue() == value) {
                return itemGroup;
            }
        }

        throw new IllegalArgumentException("Unknown menu item group: " + value);
    }
}
