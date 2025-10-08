package com.example.kinoxp.controller;

import com.example.kinoxp.DTO.theatre.ScreeningRequest;
import com.example.kinoxp.model.theatre.Screening;
import com.example.kinoxp.model.theatre.Seat;
import com.example.kinoxp.service.theatre.ScreeningService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/api/screenings")
@CrossOrigin(origins = "*")
public class ScreeningController {

    private final ScreeningService screeningService;

    public ScreeningController(ScreeningService screeningService) {
        this.screeningService = screeningService;
    }   

    @GetMapping
    public ResponseEntity<List<Screening>> getAllScreenings() {
        return ResponseEntity.ok(screeningService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Screening> getScreening(@PathVariable Integer id) {
        return ResponseEntity.ok(screeningService.getRequiredScreening(id));
    }

    @PostMapping
    public ResponseEntity<Screening> createScreening(@RequestBody ScreeningRequest request) {
        Screening screening = screeningService.save(
                request.getMovieId(),
                request.getTheatreId(),
                request.getStartTime(),
                request.getEmployeeId()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(screening);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Screening> updateScreening(
            @PathVariable Integer id,
            @RequestBody ScreeningRequest request) {

        Screening updated = screeningService.updateScreening(
                id,
                request.getMovieId(),
                request.getTheatreId(),
                request.getStartTime(),
                request.getEmployeeId()
        );

        return ResponseEntity.ok(updated);
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteScreening(@PathVariable Integer id) {
        screeningService.deleteById(id);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/{id}/available-seats")
    public ResponseEntity<List<Seat>> getAvailableSeats(@PathVariable Integer id) {
        return ResponseEntity.ok(screeningService.getAvailableSeats(id));
    }
}
