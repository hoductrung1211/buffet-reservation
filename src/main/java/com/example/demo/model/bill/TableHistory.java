package com.example.demo.model.bill;

import com.example.demo.converter.TableHistoryStatusConverter;
import com.example.demo.model.auth.Employee;
import com.example.demo.model.price.Price;
import com.example.demo.model.reservation.Reservation;
import com.example.demo.model.table.BuffetTable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(schema = "bill")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TableHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int tableHistoryId;

    @ManyToOne()
    @JoinColumn(name = "buffet_table_id")
    private BuffetTable buffetTable;

    @OneToOne()
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;

    @ManyToOne()
    @JoinColumn(name = "price_id", nullable = false)
    private Price price;

    private LocalDateTime startDateTime;

    private LocalDateTime endDateTime;

    private int adultsQuantity;

    private int childrenQuantity;

    @Convert(converter = TableHistoryStatusConverter.class)
    private TableHistoryStatus tableHistoryStatus;

    @OneToMany(mappedBy = "tableHistory", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TableHistoryMenuItem> menuItems;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id")
    private Employee employee;

    public TableHistory(
            int tableHistoryId,
            BuffetTable buffetTable,
            Reservation reservation,
            LocalDateTime startDateTime,
            LocalDateTime endDateTime,
            int adultsQuantity,
            int childrenQuantity,
            TableHistoryStatus tableHistoryStatus
    ) {
        this.tableHistoryId = tableHistoryId;
        this.buffetTable = buffetTable;
        this.reservation = reservation;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.adultsQuantity = adultsQuantity;
        this.childrenQuantity = childrenQuantity;
        this.tableHistoryStatus = tableHistoryStatus;
    }
}
