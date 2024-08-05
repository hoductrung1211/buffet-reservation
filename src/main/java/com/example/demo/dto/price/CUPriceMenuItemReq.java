package com.example.demo.dto.price;

import com.example.demo.model.menu.MenuItem;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class CUPriceMenuItemReq {
    private Integer priceMenuItemId;
    private BigDecimal price;
    private LocalDate applicationDate;
    private Integer menuItemId;
}
