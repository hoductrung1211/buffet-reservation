package com.example.demo.model.price;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(schema = "price",uniqueConstraints = @UniqueConstraint(columnNames = {"day_group_id","application_date"}))
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Price {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int priceId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "day_group_id", nullable = false)
    private DayGroup dayGroup;

    @Column(nullable = false)
    private BigDecimal adultPrice;

    @Column(nullable = false)
    private BigDecimal childPrice;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate applicationDate;

    // Constructor với tham số
    public Price(DayGroup dayGroup, BigDecimal adultPrice, BigDecimal childPrice) {
        this.dayGroup = dayGroup;
        this.adultPrice = adultPrice;
        this.childPrice = childPrice;
    }
}
