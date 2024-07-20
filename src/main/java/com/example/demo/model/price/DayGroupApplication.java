package com.example.demo.model.price;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(schema = "price")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DayGroupApplication {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int dateGroupApplicationId;

    @ManyToOne()
    @JoinColumn(name = "day_group_id")
    private DayGroup dayGroup;

    @Column(nullable = false)
    private Date applicationDate = new Date();

    private Integer dayOfWeek;

    @PrePersist
    @PreUpdate
    private void checkDayGroupApplication() {
        if (this.dayOfWeek != null && (this.dayOfWeek < 1 || this.dayOfWeek > 7)) {
            throw new IllegalArgumentException("Invalid day group application" + this.dayOfWeek + ". It must be between 1 and 7");
        }
    }
}
