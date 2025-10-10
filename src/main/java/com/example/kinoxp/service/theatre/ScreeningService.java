package com.example.kinoxp.service.theatre;

import com.example.kinoxp.model.theatre.Screening;
import com.example.kinoxp.model.theatre.Seat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public interface ScreeningService {
    boolean existsById(Integer id);
    Optional<Screening> findById(Integer id);
    List<Screening> findAll();
    Screening save(Integer movieId, Integer theatreId, LocalDateTime startTime, Integer employeeId);
    void deleteById(Integer id);
    Screening checkIfScreeningExists(Integer id);
    Screening updateScreening(Integer id, Integer movieId, Integer theatreId, LocalDateTime startTime, Integer employeeId);
    List<Screening> getScreeningsByDateRange(LocalDate startDate, LocalDate endDate, Integer theatreId);
    Screening updateScreeningSchedule(Integer screeningId, Map<String, Object> updates);
    Screening getRequiredScreening(Integer screeningId);
    List<Seat> getAvailableSeats(Integer id);
}
