package com.example.kinoxp.repository.Booking;

import com.example.kinoxp.model.booking.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TicketRepo extends JpaRepository<Ticket,Integer> {

    @Query("""
               select t  from Ticket t 
               join fetch t.seat s 
               join fetch s.theatre th 
               where t.reservation.reservationId = :reservationId
               order by s.rowNumber, s.seatNumber
            """)

    List<Ticket> findByReservationIdWithSeats(Integer reservationId);
}