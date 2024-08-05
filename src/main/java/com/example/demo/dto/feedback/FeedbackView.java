package com.example.demo.dto.feedback;

import com.example.demo.dto.menuitem.MenuItemSortView;
import com.example.demo.model.auth.Customer;
import com.example.demo.model.menu.MenuItem;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;

@Data
public class FeedbackView {
    private int feedbackId;
    private MenuItemSortView menuItem;
    private Customer customer;
    private Boolean isReliability;
    private Integer rating;
    private String comment;
    private LocalDateTime createdDateTime = LocalDateTime.now();
}
