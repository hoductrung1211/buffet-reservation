package com.example.demo.model.table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(schema = "buffet_table")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class TableGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int tableGroupId;

    @Column(nullable = false, unique = true)
    private String tableGroupName;

    @Column(nullable = false)
    private int minPeopleQuantity;

    @Column(nullable = false)
    private int maxPeopleQuantity;

    public TableGroup(int tableGroupId, String tableGroupName, int minPeopleQuantity, int maxPeopleQuantity) {
        this.tableGroupId = tableGroupId;
        this.tableGroupName = tableGroupName;
        this.minPeopleQuantity = minPeopleQuantity;
        this.maxPeopleQuantity = maxPeopleQuantity;
    }

    @OneToMany(mappedBy = "tableGroup", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference
    private List<BuffetTable> buffetTables = new ArrayList<>();

    @PrePersist
    @PreUpdate
    private void validateTableGroup() {
        if (this.minPeopleQuantity < 0) {
            throw new IllegalArgumentException("Minimum number of people must be greater than 0");
        }

        if (this.maxPeopleQuantity < 0) {
            throw new IllegalArgumentException("Maximum number of people must be greater than 0");
        }
    }
}
