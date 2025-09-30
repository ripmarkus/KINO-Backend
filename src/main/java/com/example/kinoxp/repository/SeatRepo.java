package com.example.kinoxp.repository;

import com.example.kinoxp.model.theatre.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeatRepo extends JpaRepository<Seat,Integer> {
}
