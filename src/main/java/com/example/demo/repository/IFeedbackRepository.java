package com.example.demo.repository;

import com.example.demo.model.feedback.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface IFeedbackRepository extends JpaRepository<Feedback, Integer> {

    @Query("SELECT f FROM Feedback f WHERE FUNCTION('DATE',f.createdDateTime) = :dateTime")
    List<Feedback> findAllByDate(@Param("dateTime") LocalDate localDate);

    List<Feedback> findAllByMenuItem_MenuItemId(Integer menuItemId);
}
