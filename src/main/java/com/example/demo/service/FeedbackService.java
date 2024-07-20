package com.example.demo.service;

import com.example.demo.repository.IFeedbackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FeedbackService {
    private IFeedbackRepository feedbackRepository;

    @Autowired
    public FeedbackService(IFeedbackRepository feedbackRepository) {
        this.feedbackRepository = feedbackRepository;
    }
}
