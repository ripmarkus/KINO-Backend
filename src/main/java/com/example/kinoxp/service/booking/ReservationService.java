package com.example.kinoxp.service.booking;

import com.example.kinoxp.model.booking.Reservation;

import java.util.List;
import java.util.Optional;

public interface ReservationService {
    boolean existsById(Integer id);
    Optional<Reservation> findById(Integer id);
    List<Reservation> findAll();
    Reservation save(Reservation reservation);
    void deleteById(Integer id);
    Reservation getRequiredReservation(Integer id);

}
