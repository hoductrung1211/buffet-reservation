package com.example.demo.model.menu;

import com.example.demo.model.bill.TableHistoryMenuItem;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(schema = "menu",
        uniqueConstraints = @UniqueConstraint(columnNames = {"application_date","menu_item_id"}))
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PriceMenuItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int priceMenuItemId;

    @Column(nullable = false)
    private BigDecimal price;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate applicationDate;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "menu_item_id")
    private MenuItem menuItem;

    @OneToMany(mappedBy = "priceMenuItem")
    private List<TableHistoryMenuItem> tableHistories;

    public PriceMenuItem(BigDecimal price, LocalDate applicationDate, MenuItem menuItem) {
        this.price = price;
        this.applicationDate = applicationDate;
        this.menuItem = menuItem;
    }
}
