package com.example.kinoxp.repository;

import com.example.kinoxp.model.theatre.Screening;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScreeningRepo extends JpaRepository<Screening,Integer> {
}
