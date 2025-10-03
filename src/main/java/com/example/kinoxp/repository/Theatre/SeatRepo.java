package com.example.kinoxp.repository.Theatre;

import com.example.kinoxp.model.theatre.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SeatRepo extends JpaRepository<Seat,Integer> {
    List<Seat> findByTheatre_TheatreId(Integer theatreId);
}
