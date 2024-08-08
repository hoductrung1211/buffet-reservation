package com.example.demo.dto.price;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DayGroupView {
    private int dateGroupId;
    private String dayGroupName;
    private boolean isActive = true;
}
