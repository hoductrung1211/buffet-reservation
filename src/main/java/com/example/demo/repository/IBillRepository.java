package com.example.demo.repository;

import com.example.demo.model.bill.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface IBillRepository extends JpaRepository<Bill, Integer> {
    @Query("SELECT b FROM Bill b WHERE FUNCTION('DATE',b.createDateTime) = :Date")
    List<Bill> findAllByCreateDate(@Param("Date") LocalDate date);

    @Query("SELECT b FROM Bill b WHERE FUNCTION('DATE',b.createDateTime) = :Date " +
            "AND b.tableHistory.reservation.customer.customerId = :customerId ")
    List<Bill> findAllByCreateDateOfCustomer(@Param("Date") LocalDate date,
                                   @Param("customerId") Integer customerId);

    @Query("SELECT b FROM Bill b WHERE b.tableHistory.reservation.customer.customerId = :customerId ")
    List<Bill> findAllByCustomer(@Param("customerId") Integer customerId);
}
