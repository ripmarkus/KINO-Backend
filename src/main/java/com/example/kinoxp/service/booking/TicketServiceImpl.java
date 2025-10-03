package com.example.kinoxp.service.booking;

import com.example.kinoxp.model.booking.Ticket;
import com.example.kinoxp.model.booking.Reservation;
import com.example.kinoxp.model.theatre.Seat;
import com.example.kinoxp.repository.Booking.TicketRepo;
import com.example.kinoxp.service.theatre.SeatService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class TicketServiceImpl implements TicketService {
    
    private final TicketRepo ticketRepo;
    private final SeatService seatService;
    
    public TicketServiceImpl(TicketRepo ticketRepo, SeatService seatService) {
        this.ticketRepo = ticketRepo;
        this.seatService = seatService;
    }

    public Ticket getRequiredTicket(Integer id) {
        return ticketRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Ticket not found"));
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

    @Override
    public void createTicketsForReservation(Reservation reservation, List<Integer> seatIds) {
        for (Integer seatId : seatIds) {
            Seat seat = seatService.getRequiredSeat(seatId);
            
            Ticket ticket = new Ticket();
            ticket.setReservation(reservation);
            ticket.setSeat(seat);
            
            save(ticket);
        }
    }
}
