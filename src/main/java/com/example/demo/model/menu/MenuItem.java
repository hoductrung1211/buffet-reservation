package com.example.demo.model.menu;

import com.example.demo.converter.MenuItemGroupConverter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(schema = "menu")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MenuItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int menuItemId;

    @Column(nullable = false)
    private String menuItemName;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "menu_item_category_id")
    private MenuItemCategory menuItemCategory;

    @Convert(converter = MenuItemGroupConverter.class)
    @Column(nullable = false)
    private MenuItemGroup menuItemGroup;

    private String description;

    private String imageUrl;

    private BigDecimal price;

    private boolean isActive = true;
}
