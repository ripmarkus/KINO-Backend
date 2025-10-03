package com.example.kinoxp.controller;

import com.example.kinoxp.model.theatre.Screening;
import com.example.kinoxp.model.movie.Movie;
import com.example.kinoxp.service.theatre.ScreeningService;
import com.example.kinoxp.service.movie.MovieService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/planning")
//TODO "*" skal erstattes med "http://localhost:XXXX" hvor XXXX er porteh som vores frontend kører på
@CrossOrigin(origins = "*")
public class PlanningController {

    private final ScreeningService screeningService;
    private final MovieService movieService;

    public PlanningController(ScreeningService screeningService, MovieService movieService) {
        this.screeningService = screeningService;
        this.movieService = movieService;
    }

    @PostMapping("/assign")
    public ResponseEntity<Screening> assignMovieToTheatre(@RequestBody Screening screening) {
        screening.calculateEndTime();

        Screening savedScreening = screeningService.save(screening);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedScreening);
    }

    @PutMapping("/screenings/{id}/movie/{newMovieId}")
    public ResponseEntity<Screening> replaceMovie(@PathVariable Integer id, @PathVariable Integer newMovieId) {
        Screening screening = screeningService.checkIfScreeningExists(id);
        Movie movie = movieService.getRequiredMovie(newMovieId);
        screening.setMovie(movie);
        screening.calculateEndTime(); // Recalculate end time with new movie
        Screening updatedScreening = screeningService.save(screening);
        return ResponseEntity.ok(updatedScreening);
    }
}
