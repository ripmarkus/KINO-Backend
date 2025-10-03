package com.example.kinoxp.service.theatre;

import com.example.kinoxp.model.theatre.Screening;
import com.example.kinoxp.model.theatre.Theatre;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ScreeningService {
    boolean existsById(Integer id);
    Optional<Screening> findById(Integer id);
    List<Screening> findAll();
    Screening save(Screening screening);
    void deleteById(Integer id);
    Screening checkIfScreeningExists(Integer id);

    List<Screening> getScreeningsByDateRange(LocalDate startDate, LocalDate endDate, Integer theatreId);
    Screening updateScreeningSchedule(Integer screeningId, Map<String, Object> updates);
}
