package com.example.kinoxp.service.booking;

import com.example.kinoxp.exception.EntityNotFoundException;
import com.example.kinoxp.model.booking.ReservationSeat;
import com.example.kinoxp.repository.Booking.ReservationSeatRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReservationSeatServiceImpl implements ReservationSeatService {
    
    private final ReservationSeatRepo reservationSeatRepo;

    public ReservationSeatServiceImpl(ReservationSeatRepo reservationSeatRepo) {
        this.reservationSeatRepo = reservationSeatRepo;
    }
    
    public ReservationSeat getRequiredReservationSeat(Integer id) {
        return reservationSeatRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("ReservationSeat", id));
    }

    @Override
    public boolean existsById(Integer id) {
        return reservationSeatRepo.existsById(id);
    }

    @Override
    public Optional<ReservationSeat> findById(Integer id) {
        return reservationSeatRepo.findById(id);
    }

    @Override
    public List<ReservationSeat> findAll() {
        return reservationSeatRepo.findAll();
    }

    @Override
    public ReservationSeat save(ReservationSeat reservationSeat) {
        return reservationSeatRepo.save(reservationSeat);
    }

    @Override
    public void deleteById(Integer id) {
        reservationSeatRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("ReservationSeat", id));
        reservationSeatRepo.deleteById(id);
    }

}
