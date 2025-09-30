package com.example.kinoxp.repository;

import com.example.kinoxp.model.booking.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepo extends JpaRepository<Ticket,Integer> {
}
