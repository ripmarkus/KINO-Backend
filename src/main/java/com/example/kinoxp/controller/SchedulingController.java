package com.example.kinoxp.controller;

import com.example.kinoxp.model.theatre.Screening;
import com.example.kinoxp.model.theatre.Theatre;
import com.example.kinoxp.model.movie.Movie;
import com.example.kinoxp.service.theatre.ScreeningService;
import com.example.kinoxp.service.theatre.TheatreService;
import com.example.kinoxp.service.movie.MovieService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/scheduling")
@CrossOrigin(origins = "*")
public class SchedulingController {

    private final ScreeningService screeningService;
    private final TheatreService theatreService;
    private final MovieService movieService;

    public SchedulingController(ScreeningService screeningService, TheatreService theatreService, MovieService movieService) {
        this.screeningService = screeningService;
        this.theatreService = theatreService;
        this.movieService = movieService;
    }

    @GetMapping("/screenings")
    public ResponseEntity<List<Screening>> getScreenings(@RequestParam LocalDate startDate, @RequestParam LocalDate endDate, @RequestParam(required = false) Integer theatreId) {
        return ResponseEntity.ok(screeningService.getScreeningsByDateRange(startDate, endDate, theatreId));
    }

    @GetMapping("/theatres")
    public ResponseEntity<List<Theatre>> getTheatres() {
        return ResponseEntity.ok(theatreService.findAll());
    }

    @GetMapping("/today")
    public ResponseEntity<List<Screening>> getTodayScreenings() {
        return getScreenings(LocalDate.now(), LocalDate.now(), null);
    }

    @GetMapping("/week")
    public ResponseEntity<List<Screening>> getWeekScreenings() {
        LocalDate today = LocalDate.now();
        LocalDate startOfWeek = today.minusDays(today.getDayOfWeek().getValue() - 1);
        LocalDate endOfWeek = startOfWeek.plusDays(6);
        return getScreenings(startOfWeek, endOfWeek, null);
    }

    @PostMapping("/assign")
    public ResponseEntity<Screening> assignMovieToTheatre(@RequestBody Screening screening) {
        screening.calculateEndTime();
        Screening savedScreening = screeningService.save(screening);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedScreening);
    }

    @PutMapping("/screenings/{id}")
    public ResponseEntity<Screening> changeDateOfScreening(@PathVariable Integer id, @RequestBody Map<String, Object> updates) {
        return ResponseEntity.ok(screeningService.updateScreeningSchedule(id, updates));
    }

    @PutMapping("/screenings/{id}/movie/{newMovieId}")
    public ResponseEntity<Screening> replaceMovie(@PathVariable Integer id, @PathVariable Integer newMovieId) {
        Screening screening = screeningService.checkIfScreeningExists(id);
        Movie movie = movieService.getRequiredMovie(newMovieId);
        screening.setMovie(movie);
        screening.calculateEndTime();
        Screening updatedScreening = screeningService.save(screening);
        return ResponseEntity.ok(updatedScreening);
    }
}