package com.example.kinoxp.RestController;

import com.example.kinoxp.model.theatre.Screening;
import com.example.kinoxp.model.theatre.Theatre;
import com.example.kinoxp.service.theatre.ScreeningService;
import com.example.kinoxp.service.theatre.TheatreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/gantt")
@CrossOrigin
public class GanttController {

    @Autowired
    private ScreeningService screeningService;
    @Autowired
    private TheatreService theatreService;

    @GetMapping("/screenings")
    public ResponseEntity<List<Screening>> getScreenings(@RequestParam LocalDate startDate, @RequestParam LocalDate endDate, @RequestParam(required = false) Integer theatreId) {
        List<Screening> screenings = screeningService.getScreeningsByDateRange(startDate, endDate, theatreId);
        return ResponseEntity.ok(screenings);
    }

    @GetMapping("/theatres") 
    public ResponseEntity<List<Theatre>> getTheatres() {
        List<Theatre> theatres = theatreService.findAll();
        return ResponseEntity.ok(theatres);
    }

    @GetMapping("/today")
    public ResponseEntity<List<Screening>> getTodayScreenings() {
        LocalDate today = LocalDate.now();
        return getScreenings(today, today, null);
    }

    @GetMapping("/week") 
    public ResponseEntity<List<Screening>> getWeekScreenings() {
        LocalDate today = LocalDate.now();
        LocalDate startOfWeek = today.minusDays(today.getDayOfWeek().getValue() - 1);
        LocalDate endOfWeek = startOfWeek.plusDays(6);
        return getScreenings(startOfWeek, endOfWeek, null);
    }

    @PutMapping("/screening/{screeningId}")
    public ResponseEntity<Screening> updateScreening(@PathVariable Integer screeningId, @RequestBody Map<String, Object> updates) {
        Screening updated = screeningService.updateScreeningSchedule(screeningId, updates);
        return ResponseEntity.ok(updated);
    }
}