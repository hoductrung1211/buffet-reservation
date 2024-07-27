package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateTableGroupReq {
    private String tableGroupName;
    private int minPeopleQuantity;
    private int maxPeopleQuantity;
}
