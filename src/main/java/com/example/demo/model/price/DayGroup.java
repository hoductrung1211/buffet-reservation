package com.example.demo.model.price;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(schema = "price")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class DayGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int dateGroupId;

    @Column(nullable = false,unique = true)
    private String dayGroupName;

    private boolean isActive = true;

    @JsonIgnore
    @OneToMany(mappedBy = "dayGroup", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Price> prices = new ArrayList<>();

    public DayGroup(int dateGroupId, String dayGroupName, boolean isActive) {
        this.dateGroupId = dateGroupId;
        this.dayGroupName = dayGroupName;
        this.isActive = isActive;
    }

    public Object getDayGroupById() {
        return null;
    }
}
