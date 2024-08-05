package com.example.demo.dto.bill;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CUBillReq {
    private Integer billId;
    private Integer tableHistoryId;
    private String note;
}
