package com.example.demo.repository;

import com.example.demo.model.price.Holiday;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IDayGroupApplicationRepository extends JpaRepository<Holiday, Integer> {
}
