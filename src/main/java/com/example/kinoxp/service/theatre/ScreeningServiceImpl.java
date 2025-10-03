package com.example.kinoxp.service.theatre;

import com.example.kinoxp.model.booking.Reservation;
import com.example.kinoxp.model.theatre.Screening;
import com.example.kinoxp.model.theatre.Seat;
import com.example.kinoxp.model.theatre.Theatre;
import com.example.kinoxp.repository.Booking.ReservationRepo;
import com.example.kinoxp.repository.Theatre.ScreeningRepo;
import com.example.kinoxp.repository.Theatre.TheatreRepo;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Service
public class ScreeningServiceImpl implements ScreeningService {
    
    private final ScreeningRepo screeningRepo;
    private final TheatreRepo theatreRepo;
    private final ReservationRepo reservationRepo;

    public ScreeningServiceImpl(ScreeningRepo screeningRepo, TheatreRepo theatreRepo, ReservationRepo reservationRepo) {
        this.screeningRepo = screeningRepo;
        this.theatreRepo = theatreRepo;
        this.reservationRepo = reservationRepo;
    }

    @Override
    public boolean existsById(Integer id) {
        return screeningRepo.existsById(id);
    }
    
    @Override
    public Optional<Screening> findById(Integer id) {
        return screeningRepo.findById(id);
    }
    
    @Override
    public List<Screening> findAll() {
        return screeningRepo.findAll();
    }
    
    @Override
    public Screening save(Screening screening) {
        return screeningRepo.save(screening);
    }
    
    @Override
    public void deleteById(Integer id) {
        screeningRepo.deleteById(id);
    }

    @Override
    public Screening checkIfScreeningExists(Integer id) {
        return getRequiredScreening(id);
    }

    @Override
    public Screening getRequiredScreening(Integer screeningId) {
        return screeningRepo.findById(screeningId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Screening not found"));
    }

    @Override
    public List<Screening> getScreeningsByDateRange(LocalDate startDate, LocalDate endDate, Integer theatreId) {
        // Simplified - use basic JPA findAll and filter in Java if needed
        return screeningRepo.findAll();
    }
    
    @Override
    public Screening updateScreeningSchedule(Integer screeningId, Map<String, Object> updates) {
        Screening screening = getRequiredScreening(screeningId);

        if (updates.containsKey("startTime")) {
            String startTimeStr = (String) updates.get("startTime");
            screening.setStartTime(LocalDateTime.parse(startTimeStr));
            screening.calculateEndTime();
        }
        
        if (updates.containsKey("theatreId")) {
            Integer newTheatreId = (Integer) updates.get("theatreId");
            Theatre newTheatre = theatreRepo.findById(newTheatreId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Theatre not found"));
            screening.setTheatre(newTheatre);
        }
        
        return screeningRepo.save(screening);
    }

    @Override
    public Set<Seat> getAvailableSeats(Integer screeningId) {
        Screening screening = getRequiredScreening(screeningId);
        Set<Seat> allSeats = screening.getTheatre().getSeats();

        // Find all reservations for this screening using simple JPA method
        List<Reservation> reservations = reservationRepo.findByScreeningShowId(screeningId);

        // Collect all reserved seats
        Set<Seat> reservedSeats = new HashSet<>();
        for (Reservation reservation : reservations) {
            if (reservation.getReservationSeats() != null) {
                reservation.getReservationSeats().forEach(rs -> reservedSeats.add(rs.getSeat()));
            }
        }

        // Find available seats by removing reserved seats from all seats
        Set<Seat> availableSeats = new HashSet<>(allSeats);
        availableSeats.removeAll(reservedSeats);

        return availableSeats;
    }
}
