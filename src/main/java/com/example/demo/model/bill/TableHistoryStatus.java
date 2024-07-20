package com.example.demo.model.bill;

import lombok.Getter;

@Getter
public enum TableHistoryStatus {
    RESERVED(1),
    CANCELED(2),
    FINISHED(3);

    private final int value;

    TableHistoryStatus(int value) {
        this.value = value;
    }

    public static TableHistoryStatus getFromValue(int value) {
        for (TableHistoryStatus status : TableHistoryStatus.values()) {
            if (status.value == value) {
                return status;
            }
        }
        throw new IllegalArgumentException("Unknown table history status: " + value);
    }
}
