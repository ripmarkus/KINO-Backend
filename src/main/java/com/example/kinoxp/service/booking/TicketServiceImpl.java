package com.example.kinoxp.service.booking;

import com.example.kinoxp.model.booking.Ticket;
import com.example.kinoxp.repository.Booking.TicketRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TicketServiceImpl implements TicketService {
    
    private final TicketRepo ticketRepo;
    
    public TicketServiceImpl(TicketRepo ticketRepo) {
        this.ticketRepo = ticketRepo;
    }

    @Override
    public boolean existsById(Integer id) {
        return ticketRepo.existsById(id);
    }
    
    @Override
    public Optional<Ticket> findById(Integer id) {
        return ticketRepo.findById(id);
    }
    
    @Override
    public List<Ticket> findAll() {
        return ticketRepo.findAll();
    }
    
    @Override
    public Ticket save(Ticket ticket) {
        return ticketRepo.save(ticket);
    }
    
    @Override
    public void deleteById(Integer id) {
        ticketRepo.deleteById(id);
    }
}
