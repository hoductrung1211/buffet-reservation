package com.example.demo.dto.price;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class CUPriceTicketReq {
    private int priceId;
    @NotNull
    private Integer dayGroupId;
    @Min(value = 0,message = "adultPrice must be >= 0")
    private BigDecimal adultPrice;
    @Min(value = 0,message = "childPrice must be >= 0")
    private BigDecimal childPrice;
    @NotNull
    private LocalDate applicationDate;
}
