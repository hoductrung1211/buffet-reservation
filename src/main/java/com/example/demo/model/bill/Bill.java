package com.example.demo.model.bill;

import com.example.demo.model.auth.Employee;
import com.example.demo.model.price.Price;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(schema = "bill")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Bill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int billId;

    @ManyToOne()
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @ManyToOne()
    @JoinColumn(name = "table_history_id", nullable = false)
    private TableHistory tableHistory;

    private Double vat;

    private String note;

    private BigDecimal total;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime createDateTime;

    @PrePersist
    @PreUpdate
    private void checkBill() {
        if (this.total.compareTo(BigDecimal.ZERO) < 0) {
            throw new RuntimeException("Bill totals must be greater than zero");
        }
    }

    public Bill(Employee employee, TableHistory tableHistory, Double vat, String note, BigDecimal total) {
        this.employee = employee;
        this.tableHistory = tableHistory;
        this.vat = vat;
        this.note = note;
        this.total = total;
        this.createDateTime = LocalDateTime.now();
    }
}
