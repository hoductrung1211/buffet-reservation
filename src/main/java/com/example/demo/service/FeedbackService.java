package com.example.demo.service;

import com.example.demo.dto.feedback.CUFeedbackReq;
import com.example.demo.dto.feedback.FeedbackView;
import com.example.demo.dto.feedback.GetByDataReq;
import com.example.demo.exception.BaseException;
import com.example.demo.model.auth.Customer;
import com.example.demo.model.feedback.Feedback;
import com.example.demo.model.menu.MenuItem;
import com.example.demo.repository.IFeedbackRepository;
import com.example.demo.repository.IMenuItemRepository;
import com.example.demo.repository.IReservationRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FeedbackService {
    private final IFeedbackRepository feedbackRepository;
    private final IReservationRepository reservationRepository;
    private final ContextHolderService contextHolderService;
    private final IMenuItemRepository menuItemRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public FeedbackService(IFeedbackRepository feedbackRepository, IReservationRepository reservationRepository, ContextHolderService contextHolderService, IMenuItemRepository menuItemRepository, ModelMapper modelMapper) {
        this.feedbackRepository = feedbackRepository;
        this.reservationRepository = reservationRepository;
        this.contextHolderService = contextHolderService;
        this.menuItemRepository = menuItemRepository;
        this.modelMapper = modelMapper;
    }

    @Transactional
    public ResponseEntity<?> createFeedback(CUFeedbackReq CUFeedbackReq) {
        Customer customer = (Customer) contextHolderService.getObjectFromContext();
        MenuItem menuItem = null;
        if (CUFeedbackReq.getMenuItemId() != null) {
            menuItem = menuItemRepository.findById(CUFeedbackReq.getMenuItemId())
                    .orElseThrow(() -> new BaseException(HttpStatus.NOT_FOUND, "Not found menu item with id: " + CUFeedbackReq.getMenuItemId()));

        }
        boolean isReliability = false;
        if (reservationRepository.existsByCustomer_CustomerId(customer.getCustomerId()))
            isReliability = true;
        Feedback feedback = new Feedback();
        feedback.setMenuItem(menuItem);
        feedback.setCustomer(customer);
        feedback.setIsReliability(isReliability);
        feedback.setRating(CUFeedbackReq.getRating());
        feedback.setComment(CUFeedbackReq.getComment());
        feedback.setCreatedDateTime(LocalDateTime.now());
        try {
            feedback = feedbackRepository.save(feedback);
            return ResponseEntity.ok(modelMapper.map(feedback, FeedbackView.class));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("create feedback is failed!");
        }
    }

    public ResponseEntity<?> deleteFeedback(Integer feedbackId) {
        Object object = contextHolderService.getObjectFromContext();
        Feedback feedback = feedbackRepository.findById(feedbackId)
                .orElseThrow(() -> new BaseException(HttpStatus.NOT_FOUND, "Not found feedback with id: " + feedbackId));
        if (object instanceof Customer) {
            Customer customer = (Customer) object;
            if (feedback.getCustomer().getCustomerId() != customer.getCustomerId())
                throw new BaseException(HttpStatus.BAD_REQUEST, "This feedback isn't yours, can't delete");
        }
        try {
            feedbackRepository.delete(feedback);
            return ResponseEntity.ok("delete feedback is successfully!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("delete feedback is failed!");
        }
    }

    public ResponseEntity<?> EditFeedback(CUFeedbackReq CUFeedbackReq) {
        Object object = contextHolderService.getObjectFromContext();
        if (CUFeedbackReq.getFeedbackId() == null)
            throw new BaseException(HttpStatus.NOT_FOUND, "feedback id must be not null");
        Feedback feedback = feedbackRepository.findById(CUFeedbackReq.getFeedbackId())
                .orElseThrow(() -> new BaseException(HttpStatus.NOT_FOUND, "Not found feedback"));
        if (CUFeedbackReq.getMenuItemId() != null && !menuItemRepository.existsById(CUFeedbackReq.getMenuItemId())) {
            throw new BaseException(HttpStatus.NOT_FOUND, "Not found menu item with id: " + CUFeedbackReq.getMenuItemId());
        }
        if (object instanceof Customer) {
            Customer customer = (Customer) object;
            if (feedback.getCustomer().getCustomerId() != customer.getCustomerId())
                throw new BaseException(HttpStatus.BAD_REQUEST, "This feedback isn't yours, can't edit");
        }
        feedback.setRating(CUFeedbackReq.getRating());
        feedback.setComment(CUFeedbackReq.getComment());
        try {
            feedbackRepository.save(feedback);
            return ResponseEntity.ok("Update feedback is successfully!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Update feedback is failed!");
        }
    }

    public ResponseEntity<?> getByDate(LocalDate byDataReq) {
        if (byDataReq == null)
            return ResponseEntity.ok(feedbackRepository.findAll().stream().map(
                            feedback -> modelMapper.map(feedback, FeedbackView.class)
                    ).collect(Collectors.toList())
            );
        return ResponseEntity.ok(feedbackRepository.findAllByDate(byDataReq)
                .stream().map(
                        feedback -> modelMapper.map(feedback, FeedbackView.class)
                ).collect(Collectors.toList())
        );
    }

    public ResponseEntity<?> getByMenuItem(Integer menuItemId) {
        if(!menuItemRepository.existsById(menuItemId))
            return ResponseEntity.badRequest().body("Not found menu item");
        return ResponseEntity.ok(feedbackRepository.findAllByMenuItem_MenuItemId(menuItemId)
                .stream().map(
                        feedback -> modelMapper.map(feedback,FeedbackView.class)
                ).collect(Collectors.toList())
        );
    }
}
