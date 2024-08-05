package com.example.demo.dto.order;

import com.example.demo.dto.price.PriceMenuItemView;
import com.example.demo.dto.tablehistory.TableHistoryView;
import com.example.demo.model.bill.TableHistory;
import com.example.demo.model.menu.PriceMenuItem;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class OrderView {
    private TableHistoryView tableHistory;
    private PriceMenuItemView priceMenuItem;
    private int menuItemQuantity;
    private LocalDateTime createDateTime;
}
