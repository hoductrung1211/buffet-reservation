package com.example.demo.model.feedback;

import com.example.demo.model.auth.Customer;
import com.example.demo.model.menu.MenuItem;
import com.example.demo.model.reservation.Reservation;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(schema = "feedback")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int feedbackId;

    @ManyToOne()
    @JoinColumn(name = "menu_item_id")
    private MenuItem menuItem;

    @ManyToOne()
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @Column(name = "is_reliability")
    private Boolean isReliability;

    private Integer rating;

    private String comment;

    @Column(nullable = false)
    private LocalDateTime createdDateTime = LocalDateTime.now();

    private void checkFeedback() {
        if (this.rating < 1 || this.rating > 5) {
            throw new IllegalArgumentException("Rating must be between 1 and 5");
        }
    }
}
