package com.example.demo.dto.tablehistory;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UMenuTableHistoryReq {
    private Integer tableHistoryId;
    private Integer menuItemQuantity;
    private Integer priceMenuItemId;
    //    private List<Order> orderList;
//    @Getter
//    @Setter
//    public static class Order{
//
//        private Integer priceMenuItemId;
//        private Integer menuItemQuantity;
//    }
}
