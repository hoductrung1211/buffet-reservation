package com.example.demo.dto.bill;

import com.example.demo.dto.employee.EmployeeView;
import com.example.demo.dto.tablehistory.TableHistoryView;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class BillView {
    private int billId;
    private EmployeeView employee;
    private TableHistoryView tableHistory;
    private Double vat;
    private String note;
    private BigDecimal total;
}
