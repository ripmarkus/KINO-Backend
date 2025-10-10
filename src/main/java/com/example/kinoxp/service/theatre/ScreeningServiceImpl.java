package com.example.kinoxp.service.theatre;

import com.example.kinoxp.exception.EntityNotFoundException;
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
import com.example.kinoxp.repository.employee.EmployeeRepo;
import com.example.kinoxp.repository.movie.MovieRepo;
import com.example.kinoxp.service.movie.MovieServiceImpl;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static com.example.kinoxp.model.theatre.ScreeningStatus.SCHEDULED;

@Service
public class ScreeningServiceImpl implements ScreeningService {
    
    private final ScreeningRepo screeningRepo;
    private final TheatreRepo theatreRepo;
    private final SeatRepo seatRepo;
    private final ReservationSeatRepo reservationSeatRepo;
    private final MovieRepo movieRepo;
    private final EmployeeRepo employeeRepo;


    public ScreeningServiceImpl(ScreeningRepo screeningRepo,
                                TheatreRepo theatreRepo,
                                SeatRepo seatRepo,
                                ReservationSeatRepo reservationSeatRepo,
                                MovieRepo movieRepo, EmployeeRepo employeeRepo) {
        this.screeningRepo = screeningRepo;
        this.theatreRepo = theatreRepo;
        this.seatRepo = seatRepo;
        this.reservationSeatRepo = reservationSeatRepo;
        this.movieRepo = movieRepo;
        this.employeeRepo = employeeRepo;
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
    public Screening save(Integer movieId, Integer theatreId, LocalDateTime startTime, Integer employeeId) {
        Screening screening = new Screening();
        screening.setMovie(movieRepo.findById(movieId).orElse(null));
        screening.setTheatre(theatreRepo.findById(theatreId).orElse(null));
        screening.setStartTime(startTime);
        screening.calculateEndTime();
        screening.setStatus(SCHEDULED);
        screening.setOperator(employeeRepo.findById(employeeId).orElse(null));
        return screeningRepo.save(screening);
    }
    
    @Override
    public void deleteById(Integer id) {
        screeningRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Screening", id));
        screeningRepo.deleteById(id);
    }

    @Override
    public Screening checkIfScreeningExists(Integer id) {
        return getRequiredScreening(id);
    }

    @Override
    public Screening getRequiredScreening(Integer screeningId) {
        return screeningRepo.findByIdWithTheatre(screeningId)
                .orElseThrow(() -> new EntityNotFoundException("Screening", screeningId));
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
                .orElseThrow(() -> new EntityNotFoundException("Theatre", newTheatreId));
            screening.setTheatre(newTheatre);
        }

        return screeningRepo.save(screening);
    }

    @Override
    public Screening updateScreening(Integer id, Integer movieId, Integer theatreId, LocalDateTime startTime, Integer employeeId) {
        Screening existing = screeningRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Screening", id));

        if (movieId != null) {
            existing.setMovie(movieRepo.findById(movieId)
                    .orElseThrow(() -> new EntityNotFoundException("Movie", movieId)));
        }

        if (theatreId != null) {
            existing.setTheatre(theatreRepo.findById(theatreId)
                    .orElseThrow(() -> new EntityNotFoundException("Theatre", theatreId)));
        }

        if (startTime != null) {
            existing.setStartTime(startTime);
            existing.calculateEndTime();
        }

        if (employeeId != null) {
            existing.setOperator(employeeRepo.findById(employeeId)
                    .orElseThrow(() -> new EntityNotFoundException("Employee", employeeId)));
        }

        return screeningRepo.save(existing);
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
