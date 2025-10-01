package com.example.kinoxp.repository.Booking;

import com.example.kinoxp.model.booking.Reservation;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ReservationRepo extends JpaRepository<Reservation,Integer> {
    @EntityGraph(attributePaths = {
            "customer",
            "screening.movie",
            "screening.theatre"
    })
    Optional<Reservation> findByReservationId(Integer id);

}