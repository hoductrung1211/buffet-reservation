package com.example.demo.model.table;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(schema = "buffet_table")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BuffetTable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int buffetTableId;
    @Column(nullable = false, unique = true)
    private String buffetTableName;
    @ManyToOne()
    @JoinColumn(name = "table_group_id", nullable = false)
    private TableGroup tableGroup;
}
