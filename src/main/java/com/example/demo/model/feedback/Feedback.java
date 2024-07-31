package com.example.demo.model.feedback;

import com.example.demo.model.auth.Customer;
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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int feedbackId;

    @ManyToOne()
    @JoinColumn(name = "reservation_id", nullable = false)
    private Reservation reservation;

    private int rating;

    private String comment;

    @Column(nullable = false)
    private LocalDateTime createdDateTime = LocalDateTime.now();

    private void checkFeedback() {
        if (this.rating < 1 || this.rating > 5) {
            throw new IllegalArgumentException("Rating must be between 1 and 5");
        }
    }
}
