package com.example.demo.model.bill;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TableHistoryMenuItemId implements Serializable {
    private int tableHistory;
    private int priceMenuItem;
}
