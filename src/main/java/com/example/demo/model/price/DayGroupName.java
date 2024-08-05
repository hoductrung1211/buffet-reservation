package com.example.demo.model.price;

import com.example.demo.model.menu.MenuItemGroup;
import lombok.Getter;

@Getter
public enum DayGroupName {
    WEEKDAY(1),
    WEEKEND(2),
    HOLIDAY(3);
    private final int value;

    DayGroupName(final int value) {
        this.value = value;
    }

    public static DayGroupName getFromValue(final int value) {
        for (final DayGroupName itemGroup : DayGroupName.values()) {
            if (itemGroup.getValue() == value) {
                return itemGroup;
            }
        }

        throw new IllegalArgumentException("Unknown DayGroupName : " + value);
    }
}
