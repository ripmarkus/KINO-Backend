package com.example.kinoxp.service.booking;

import com.example.kinoxp.model.booking.ReservationSeat;
import com.example.kinoxp.model.theatre.Seat;

import java.util.List;
import java.util.Optional;

public interface ReservationSeatService {
    boolean existsById(Integer id);
    Optional<ReservationSeat> findById(Integer id);
    List<ReservationSeat> findAll();
    ReservationSeat save(ReservationSeat reservationSeat);
    void deleteById(Integer id);

}
