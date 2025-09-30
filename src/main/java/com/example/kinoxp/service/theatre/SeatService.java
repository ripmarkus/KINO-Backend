package com.example.kinoxp.service.theatre;

import com.example.kinoxp.model.booking.Ticket;

import java.util.List;

public interface SeatService {
    void existsById(Integer id);
    void findById(Integer id);
    List<Iterable> findAll();
    void save(Ticket ticket);
    void deleteById(Integer id);
}
