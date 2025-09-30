package com.example.kinoxp.repository;

import com.example.kinoxp.model.booking.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepo extends JpaRepository<Reservation,Integer> {
}
