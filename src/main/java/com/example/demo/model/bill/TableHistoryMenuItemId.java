package com.example.demo.model.bill;

import jakarta.persistence.Column;

import java.io.Serializable;
import java.util.Objects;

public class TableHistoryMenuItemId implements Serializable {
    private static final long serialVersionUID = 1L;

    @Column(name = "table_history_id")
    private int tableHistoryId;

    @Column(name = "menu_item_id")
    private int menuItemId;

    public TableHistoryMenuItemId() {}

    public TableHistoryMenuItemId(int tableHistoryId, int menuItemId) {
        this.tableHistoryId = tableHistoryId;
        this.menuItemId = menuItemId;
    }

    // hashCode and equals methods
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TableHistoryMenuItemId that = (TableHistoryMenuItemId) o;
        return tableHistoryId == that.tableHistoryId &&
                menuItemId == that.menuItemId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(tableHistoryId, menuItemId);
    }
}
