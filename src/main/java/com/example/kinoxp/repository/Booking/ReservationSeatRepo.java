package com.example.kinoxp.repository.Booking;

import com.example.kinoxp.model.booking.ReservationSeat;
import com.example.kinoxp.model.theatre.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReservationSeatRepo extends JpaRepository<ReservationSeat, Integer> {
    
    // SIMPLIFIED: Use a direct query to get just the seat IDs and then fetch seats separately
    @Query(value = "SELECT seat_seat_id FROM reservation_seat WHERE screening_show_id = :screeningId", nativeQuery = true)
    List<Integer> findSeatIdsByScreeningShowId(@Param("screeningId") Integer screeningId);
}
