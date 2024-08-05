package com.example.demo.dto.tablehistory;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CUTableHistoryReq {
    private Integer tableHistoryId;
    private Integer adultsQuantity;
    private Integer childrenQuantity;
    private String status;
}
