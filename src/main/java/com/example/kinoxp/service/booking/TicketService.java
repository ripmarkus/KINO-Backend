package com.example.kinoxp.service.booking;

import com.example.kinoxp.model.booking.Ticket;

import java.util.List;

public interface TicketService {
    void existsById(Integer id);
    void findById(Integer id);
    List<Iterable> findAll();
    void save(Ticket ticket);
    void deleteById(Integer id);
}
