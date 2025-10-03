package com.example.kinoxp.service.theatre;

import com.example.kinoxp.model.theatre.Screening;
import com.example.kinoxp.model.theatre.Theatre;
import com.example.kinoxp.repository.Theatre.ScreeningRepo;
import com.example.kinoxp.repository.Theatre.TheatreRepo;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ScreeningServiceImpl implements ScreeningService {
    
    private final ScreeningRepo screeningRepo;
    private final TheatreRepo theatreRepo;
    
    public ScreeningServiceImpl(ScreeningRepo screeningRepo, TheatreRepo theatreRepo) {
        this.screeningRepo = screeningRepo;
        this.theatreRepo = theatreRepo;
    }

    public Screening checkIfScreeningExists(Integer id) {
        return screeningRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Screening not found"));
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
    public List<Screening> getScreeningsByDateRange(LocalDate startDate, LocalDate endDate, Integer theatreId) {
        LocalDateTime startDateTime = startDate.atStartOfDay();
        LocalDateTime endDateTime = endDate.atTime(LocalTime.MAX);
        
        if (theatreId != null) {
            return screeningRepo.findByTheatreTheatreIdAndStartTimeBetween(theatreId, startDateTime, endDateTime);
        } else {
            return screeningRepo.findByStartTimeBetween(startDateTime, endDateTime);
        }
    }
    
    @Override
    public Screening updateScreeningSchedule(Integer screeningId, Map<String, Object> updates) {
        Screening screening = checkIfScreeningExists(screeningId);
        
        // Opdater start tid hvis angivet
        if (updates.containsKey("startTime")) {
            String startTimeStr = (String) updates.get("startTime");
            screening.setStartTime(LocalDateTime.parse(startTimeStr));
            screening.calculateEndTime(); // Beregn ny slut tid
        }
        
        // Skift sal hvis angivet
        if (updates.containsKey("theatreId")) {
            Integer newTheatreId = (Integer) updates.get("theatreId");
            Theatre newTheatre = theatreRepo.findById(newTheatreId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Theatre not found"));
            screening.setTheatre(newTheatre);
        }
        
        // Tjek for konflikter
        if (hasTimeConflicts(screening)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Screening conflicts with existing screenings");
        }
        
        return screeningRepo.save(screening);
    }
    
    private boolean hasTimeConflicts(Screening screening) {
        List<Screening> overlapping = screeningRepo.findOverlappingScreenings(
            screening.getTheatre().getTheatreId(),
            screening.getStartTime(),
            screening.getEndTime()
        );
        
        // Fjern den screening vi opdaterer fra konflikt check
        overlapping.removeIf(s -> s.getShowId().equals(screening.getShowId()));
        
        return !overlapping.isEmpty();
    }
}
