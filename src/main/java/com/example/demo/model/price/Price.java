package com.example.demo.model.price;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(schema = "price")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Price {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int priceId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "day_group_id", nullable = false)
    @JsonBackReference
    private DayGroup dayGroup;

    @Column(nullable = false)
    private BigDecimal adultPrice;

    @Column(nullable = false)
    private BigDecimal childPrice;

    // Constructor với tham số
    public Price(DayGroup dayGroup, BigDecimal adultPrice, BigDecimal childPrice) {
        this.dayGroup = dayGroup;
        this.adultPrice = adultPrice;
        this.childPrice = childPrice;
    }
}
