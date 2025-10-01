package com.example.kinoxp.service.booking;

import com.example.kinoxp.model.booking.Reservation;
import com.example.kinoxp.repository.ReservationRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReservationServiceImpl implements ReservationService {
    
    private final ReservationRepo reservationRepo;
    
    public ReservationServiceImpl(ReservationRepo reservationRepo) {
        this.reservationRepo = reservationRepo;
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
