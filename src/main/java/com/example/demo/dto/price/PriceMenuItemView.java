package com.example.demo.dto.price;

import com.example.demo.dto.menuitem.MenuItemSortView;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class PriceMenuItemView {
    private int priceMenuItemId;
    private BigDecimal price;
    private LocalDate applicationDate;
    private MenuItemSortView menuItem;
}
