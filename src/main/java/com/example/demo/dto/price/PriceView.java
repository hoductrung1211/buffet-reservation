package com.example.demo.dto.price;

import com.example.demo.model.price.DayGroup;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class PriceView {
    private int priceId;
    private DayGroup dayGroup;
    private BigDecimal adultPrice;
    private BigDecimal childPrice;
    private LocalDate applicationDate;
}
