package com.example.demo.model.menu;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.awt.*;
import java.util.List;

@Entity
@Table(schema = "menu")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MenuItemCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int menuItemCategoryId;
    @Column(nullable = false)
    private String menuItemCategoryName;

    @ManyToOne()
    @JoinColumn(name = "sup_menu_item_category_id")
    private MenuItemCategory supMenuItemCategory;

    @OneToMany(mappedBy = "supMenuItemCategory", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MenuItemCategory> menuItemCategories;

    @OneToMany(mappedBy = "menuItemCategory", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MenuItem> menuItems;
}
