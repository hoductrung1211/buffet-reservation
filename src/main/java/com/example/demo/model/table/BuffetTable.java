package com.example.demo.model.table;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(schema = "buffet_table")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class BuffetTable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int buffetTableId;

    @Column(nullable = false, unique = true)
    private String buffetTableName;

    @ManyToOne()
    @JoinColumn(name = "table_group_id", nullable = false)
    @JsonManagedReference
    private TableGroup tableGroup;
}
