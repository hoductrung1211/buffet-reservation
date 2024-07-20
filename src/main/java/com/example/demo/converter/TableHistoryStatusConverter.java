package com.example.demo.converter;

import com.example.demo.model.bill.TableHistoryStatus;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class TableHistoryStatusConverter implements AttributeConverter<TableHistoryStatus, Integer> {

    @Override
    public Integer convertToDatabaseColumn(TableHistoryStatus tableHistoryStatus) {
        return tableHistoryStatus != null ? tableHistoryStatus.getValue() : null;
    }

    @Override
    public TableHistoryStatus convertToEntityAttribute(Integer value) {
        return value != null ? TableHistoryStatus.getFromValue(value) : null;
    }
}
