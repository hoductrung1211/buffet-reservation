package com.example.demo.controller;

import com.example.demo.dto.feedback.CUFeedbackReq;
import com.example.demo.dto.feedback.GetByDataReq;
import com.example.demo.service.FeedbackService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/feedbacks")
public class FeedbackController {
    private final FeedbackService feedbackService;

    @Autowired
    public FeedbackController(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    @PreAuthorize("hasAuthority('CUSTOMER')")
    @PostMapping("")
    public ResponseEntity<?> createFeedback(@Valid @RequestBody CUFeedbackReq CUFeedbackReq){
        return feedbackService.createFeedback(CUFeedbackReq);
    }

    @DeleteMapping("")
    public ResponseEntity<?> deleteFeedback(@RequestParam(name = "feedbackId") Integer feedbackId){
        return feedbackService.deleteFeedback(feedbackId);
    }

    @PutMapping("")
    public ResponseEntity<?> editFeedback(@Valid @RequestBody CUFeedbackReq CUFeedbackReq){
        return feedbackService.EditFeedback(CUFeedbackReq);
    }

    @GetMapping("get-by-date")
    public ResponseEntity<?> getByDate(@RequestParam(name = "dateReq")
                                           @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                           LocalDate byDataReq){
        return feedbackService.getByDate(byDataReq);
    }

    @GetMapping("get-by-menuitem")
    public ResponseEntity<?> getByMenuItem(@RequestParam(name = "menuItemId") Integer menuItemId){
        return feedbackService.getByMenuItem(menuItemId);
    }


}
