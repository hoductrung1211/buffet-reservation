package com.example.demo.dto.tablehistory;

import com.example.demo.converter.TableHistoryStatusConverter;
import com.example.demo.dto.employee.EmployeeView;
import com.example.demo.dto.price.PriceView;
import com.example.demo.dto.reservation.ReservationView;
import com.example.demo.model.auth.Employee;
import com.example.demo.model.bill.TableHistoryStatus;
import com.example.demo.model.reservation.Reservation;
import com.example.demo.model.table.BuffetTable;
import jakarta.persistence.Convert;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class TableHistoryView {
    private int tableHistoryId;
    private BuffetTable buffetTable;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private int adultsQuantity;
    private int childrenQuantity;
    private TableHistoryStatus tableHistoryStatus;
    private EmployeeView employee;
    private PriceView price;
    private ReservationView reservation;
}
