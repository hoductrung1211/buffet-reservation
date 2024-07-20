package com.example.demo.model.bill;

import com.example.demo.model.auth.Employee;
import com.example.demo.model.price.Price;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(schema = "bill")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Bill {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int billId;

    @ManyToOne()
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @ManyToOne()
    @JoinColumn(name = "table_history_id", nullable = false)
    private TableHistory tableHistory;

    @ManyToOne()
    @JoinColumn(name = "price_id", nullable = false)
    private Price price;

    private int vat;

    private String note;

    private BigDecimal total;

    @PrePersist
    @PreUpdate
    private void checkBill() {
        if (this.total.compareTo(BigDecimal.ZERO) < 0) {
            throw new RuntimeException("Bill totals must be greater than zero");
        }
    }
}
