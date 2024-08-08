package com.example.demo.model.bill;

import com.example.demo.model.menu.PriceMenuItem;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(schema = "bill")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@IdClass(TableHistoryMenuItemId.class)
public class TableHistoryMenuItem implements Serializable {

    @Id
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "table_history_id", nullable = false)
    private TableHistory tableHistory;

    @Id
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "price_menu_item_id", nullable = false)
    private PriceMenuItem priceMenuItem;

    @Column(nullable = false)
    private int menuItemQuantity;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime createDateTime;

    public TableHistoryMenuItem(TableHistory tableHistory, PriceMenuItem priceMenuItem, int menuItemQuantity) {
        this.tableHistory = tableHistory;
        this.priceMenuItem = priceMenuItem;
        this.menuItemQuantity = menuItemQuantity;
        this.createDateTime = LocalDateTime.now();
    }
}
