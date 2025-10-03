package com.example.kinoxp.service.booking;

import com.example.kinoxp.model.booking.Reservation;
import com.example.kinoxp.model.customer.Customer;
import com.example.kinoxp.model.theatre.Screening;
import com.example.kinoxp.repository.Booking.ReservationRepo;
import com.example.kinoxp.service.customer.CustomerService;
import com.example.kinoxp.service.theatre.ScreeningService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ReservationServiceImpl implements ReservationService {
    
    private final ReservationRepo reservationRepo;
    private final ScreeningService screeningService;
    private final CustomerService customerService;
    
    public ReservationServiceImpl(ReservationRepo reservationRepo, ScreeningService screeningService, 
                                CustomerService customerService) {
        this.reservationRepo = reservationRepo;
        this.screeningService = screeningService;
        this.customerService = customerService;
    }

    public Reservation getRequiredReservation(Integer id) {
        return reservationRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Reservation not found"));
    }

    @Override
    public boolean existsById(Integer id) {
        return reservationRepo.existsById(id);
    }
    
    @Override
    public Optional<Reservation> findById(Integer id) {
        return reservationRepo.findById(id);
    }
    
    @Override
    public List<Reservation> findAll() {
        return reservationRepo.findAll();
    }
    
    @Override
    public Reservation save(Reservation reservation) {
        return reservationRepo.save(reservation);
    }
    
    @Override
    public void deleteById(Integer id) {
        reservationRepo.deleteById(id);
    }

}
