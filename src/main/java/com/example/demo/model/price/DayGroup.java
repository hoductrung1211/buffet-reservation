package com.example.demo.model.price;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(schema = "price")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DayGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int dateGroupId;

    @Column(nullable = false)
    private String dayGroupName;

    private boolean isActive = true;
    @OneToMany(mappedBy = "dayGroup", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<DayGroupApplication> dayGroupApplications = new ArrayList<>();

    @OneToMany(mappedBy = "dayGroup", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Price> prices = new ArrayList<>();

    public DayGroup(int dateGroupId, String dayGroupName, boolean isActive) {
        this.dateGroupId = dateGroupId;
        this.dayGroupName = dayGroupName;
        this.isActive = isActive;
    }
}
