package com.example.kinoxp.service.theatre;

import com.example.kinoxp.model.theatre.Seat;
import com.example.kinoxp.repository.Theatre.SeatRepo;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class SeatServiceImpl implements SeatService {
    
    private final SeatRepo seatRepo;
    
    public SeatServiceImpl(SeatRepo seatRepo) {
        this.seatRepo = seatRepo;
    }

    public Seat getRequiredSeat(Integer id) {
        return seatRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Seat not found"));
    }

    @Override
    public boolean existsById(Integer id) {
        return seatRepo.existsById(id);
    }
    
    @Override
    public Optional<Seat> findById(Integer id) {
        return seatRepo.findById(id);
    }
    
    @Override
    public List<Seat> findAll() {
        return seatRepo.findAll();
    }
    
    @Override
    public Seat save(Seat seat) {
        return seatRepo.save(seat);
    }
    
    @Override
    public void deleteById(Integer id) {
        seatRepo.deleteById(id);
    }

    @Override
    public List<Seat> findByTheatreId(Integer theatreId) {
        return seatRepo.findByTheatre_TheatreId(theatreId);
    }
}
