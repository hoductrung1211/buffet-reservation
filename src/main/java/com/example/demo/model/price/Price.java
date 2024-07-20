package com.example.demo.model.price;

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
    private DayGroup dayGroup;

    @Column(nullable = false)
    private BigDecimal adultPrice;

    @Column(nullable = false)
    private BigDecimal childPrice;
}
