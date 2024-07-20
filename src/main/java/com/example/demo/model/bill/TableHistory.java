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
}
