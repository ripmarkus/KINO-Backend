package com.example.kinoxp.service.theatre;

import com.example.kinoxp.model.theatre.Seat;

import java.util.List;
import java.util.Optional;

public interface SeatService {
    boolean existsById(Integer id);
    Optional<Seat> findById(Integer id);
    List<Seat> findAll();
    Seat save(Seat seat);
    void deleteById(Integer id);
    Seat getRequiredSeat(Integer id);
    
    // Basic method for getting theatre seats
    List<Seat> findByTheatreId(Integer theatreId);
}
