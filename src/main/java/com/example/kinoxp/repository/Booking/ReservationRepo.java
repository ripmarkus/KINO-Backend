package com.example.kinoxp.repository.Booking;

import com.example.kinoxp.model.booking.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationRepo extends JpaRepository<Reservation,Integer> {
    // Standard JPA methods only - findById, findAll, save, deleteById etc.

    // Simple Spring Data JPA method to find reservations by screening ID
    List<Reservation> findByScreeningShowId(Integer screeningId);
}