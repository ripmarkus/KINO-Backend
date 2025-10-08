package com.example.kinoxp.repository.Booking;

import com.example.kinoxp.model.booking.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationRepo extends JpaRepository<Reservation,Integer> {
    List<Reservation> findByScreening_ShowId(Integer screeningShowId);
}