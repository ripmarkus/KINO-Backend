package com.example.kinoxp.service.booking;

import com.example.kinoxp.model.booking.Ticket;
import com.example.kinoxp.model.booking.Reservation;

import java.util.List;
import java.util.Optional;

public interface TicketService {
    boolean existsById(Integer id);
    Optional<Ticket> findById(Integer id);
    List<Ticket> findAll();
    Ticket save(Ticket ticket);
    void deleteById(Integer id);
    Ticket getRequiredTicket(Integer id);
    
    // Simplified method for creating tickets for a reservation
    void createTicketsForReservation(Reservation reservation, List<Integer> seatIds);
}
