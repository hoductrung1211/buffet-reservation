package com.example.demo.model.bill;

import com.example.demo.converter.TableHistoryStatusConverter;
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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int tableHistoryId;

    @ManyToOne()
    @JoinColumn(name = "buffet_table_id")
    private BuffetTable buffetTable;

    @ManyToOne()
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;

    private LocalDateTime startDateTime;

    private LocalDateTime endDateTime;

    private int adultsQuantity;

    private int childrenQuantity;

    @Convert(converter = TableHistoryStatusConverter.class)
    private TableHistoryStatus tableHistoryStatus;

    @OneToMany(mappedBy = "tableHistory", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TableHistoryMenuItem> menuItems;

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
