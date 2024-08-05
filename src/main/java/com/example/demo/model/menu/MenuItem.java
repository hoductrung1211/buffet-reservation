package com.example.demo.model.menu;

import com.example.demo.converter.MenuItemGroupConverter;
import com.example.demo.model.bill.TableHistoryMenuItem;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(schema = "menu")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MenuItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    private boolean isActive = true;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "menuItem",cascade = CascadeType.ALL)
    private List<PriceMenuItem> priceMenuItemList;

    public MenuItem(String menuItemName, MenuItemCategory menuItemCategory, MenuItemGroup menuItemGroup, String description, String imageUrl, boolean isActive) {
        this.menuItemName = menuItemName;
        this.menuItemCategory = menuItemCategory;
        this.menuItemGroup = menuItemGroup;
        this.description = description;
        this.imageUrl = imageUrl;
        this.isActive = isActive;
    }
}
