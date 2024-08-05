package com.example.demo.dto.feedback;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

@Data
public class CUFeedbackReq {
    private Integer feedbackId;
    private Integer menuItemId;
    @Range(min = 1,max = 5,message = "Rating must be between 1 and 5")
    private Integer rating;
    private String comment;
}
