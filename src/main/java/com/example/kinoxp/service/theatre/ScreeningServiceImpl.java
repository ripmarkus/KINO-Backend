package com.example.kinoxp.service.theatre;

import com.example.kinoxp.model.booking.Reservation;
import com.example.kinoxp.model.booking.ReservationSeat;
import com.example.kinoxp.model.theatre.Screening;
import com.example.kinoxp.model.theatre.Seat;
import com.example.kinoxp.model.theatre.Theatre;
import com.example.kinoxp.repository.Booking.ReservationRepo;
import com.example.kinoxp.repository.Booking.ReservationSeatRepo;
import com.example.kinoxp.repository.Theatre.ScreeningRepo;
import com.example.kinoxp.repository.Theatre.SeatRepo;
import com.example.kinoxp.repository.Theatre.TheatreRepo;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ScreeningServiceImpl implements ScreeningService {
    
    private final ScreeningRepo screeningRepo;
    private final TheatreRepo theatreRepo;
    private final SeatRepo seatRepo;
    private final ReservationSeatRepo reservationSeatRepo;


    public ScreeningServiceImpl(ScreeningRepo screeningRepo,
                                TheatreRepo theatreRepo,
                                SeatRepo seatRepo,
                                ReservationSeatRepo reservationSeatRepo) {
        this.screeningRepo = screeningRepo;
        this.theatreRepo = theatreRepo;
        this.seatRepo = seatRepo;
        this.reservationSeatRepo = reservationSeatRepo;
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
        return screeningRepo.findByIdWithTheatre(screeningId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Screening not found"));
    }

    @Override
    public List<Screening> getScreeningsByDateRange(LocalDate startDate, LocalDate endDate, Integer theatreId) {
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
    public List<Seat> getAvailableSeats(Integer screeningId) {
        Screening screening = getRequiredScreening(screeningId);
        Integer theatreId = screening.getTheatre().getTheatreId();

        List<Seat> allSeats = Optional.ofNullable(seatRepo.findByTheatre_TheatreId(theatreId))
                .orElse(Collections.emptyList());

        List<Integer> reservedSeatIds = reservationSeatRepo.findSeatIdsByScreeningShowId(screeningId);

        return allSeats.stream()
                .filter(seat -> !reservedSeatIds.contains(seat.getSeatId()))
                .sorted(Comparator
                        .comparing(Seat::getRowNumber)
                        .thenComparing(Seat::getSeatNumber))
                .toList();
    }

}
