package com.example.kinoxp.service.booking;

import com.example.kinoxp.model.booking.Ticket;

import java.util.List;
import java.util.Optional;

public interface TicketService {
    boolean existsById(Integer id);
    Optional<Ticket> findById(Integer id);
    List<Ticket> findAll();
    Ticket save(Ticket ticket);
    void deleteById(Integer id);
}
