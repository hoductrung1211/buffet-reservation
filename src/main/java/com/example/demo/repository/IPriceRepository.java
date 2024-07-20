package com.example.demo.repository;

import com.example.demo.model.price.Price;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPriceRepository extends JpaRepository<Price, Integer> {
}
