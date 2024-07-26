package com.example.demo.model.bill;

import com.example.demo.model.menu.MenuItem;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(schema = "bill")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TableHistoryMenuItem {
    @EmbeddedId
    private TableHistoryMenuItemId id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId("tableHistoryId")
    @JoinColumn(name = "table_history_id", nullable = false)
    private TableHistory tableHistory;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId("menuItemId")
    @JoinColumn(name = "menu_item_id", nullable = false)
    private MenuItem menuItem;

    @Column(nullable = false)
    private int menuItemQuantity;
}
