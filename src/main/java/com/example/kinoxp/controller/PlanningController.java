package com.example.kinoxp.controller;

import com.example.kinoxp.model.theatre.Screening;
import com.example.kinoxp.model.theatre.Seat;
import com.example.kinoxp.model.movie.Movie;
import com.example.kinoxp.service.theatre.ScreeningService;
import com.example.kinoxp.service.theatre.SeatService;
import com.example.kinoxp.service.movie.MovieService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;

@RestController
@RequestMapping("/api/planning")
//TODO "*" skal erstattes med "http://localhost:XXXX" hvor XXXX er porteh som vores frontend kører på
@CrossOrigin(origins = "*")
public class PlanningController {

    private final ScreeningService screeningService;
    private final MovieService movieService;
    private final SeatService seatService;

    public PlanningController(ScreeningService screeningService, MovieService movieService, SeatService seatService) {
        this.screeningService = screeningService;
        this.movieService = movieService;
        this.seatService = seatService;
    }

    @PostMapping("/assign")
    public ResponseEntity<Screening> assignMovieToTheatre(@RequestBody Screening screening) {
        screening.calculateEndTime();
        if (screening.getTheatre() != null) {
            List<Seat> theatreSeats = seatService.findByTheatreId(screening.getTheatre().getTheatreId());
            screening.initializeSeats(new HashSet<>(theatreSeats));
        }
        
        Screening savedScreening = screeningService.save(screening);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedScreening);
    }

    @GetMapping("/screenings")
    public ResponseEntity<List<Screening>> getAllScreenings() {
        return ResponseEntity.ok(screeningService.findAll());
    }

    @GetMapping("/screenings/{id}")
    public ResponseEntity<Screening> getScreening(@PathVariable Integer id) {
        Screening screening = screeningService.checkIfScreeningExists(id);
        return ResponseEntity.ok(screening);
    }

    @PutMapping("/screenings/{id}")
    public ResponseEntity<Screening> updateAssignment(@PathVariable Integer id, @RequestBody Screening screening) {
        Screening existing = screeningService.checkIfScreeningExists(id);
        screening.setShowId(existing.getShowId()); // ensure ID consistency
        Screening updatedScreening = screeningService.save(screening);
        return ResponseEntity.ok(updatedScreening);
    }

    @DeleteMapping("/screenings/{id}")
    public ResponseEntity<Void> removeAssignment(@PathVariable Integer id) {
        Screening screening = screeningService.checkIfScreeningExists(id);
        screeningService.deleteById(screening.getShowId());
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/screenings/{id}/movie/{newMovieId}")
    public ResponseEntity<Screening> replaceMovie(@PathVariable Integer id, @PathVariable Integer newMovieId) {
        Screening screening = screeningService.checkIfScreeningExists(id);
        Movie movie =   movieService.getRequiredMovie(newMovieId);
        screening.setMovie(movie);
        Screening updatedScreening = screeningService.save(screening);
        return ResponseEntity.ok(updatedScreening);
    }
}
