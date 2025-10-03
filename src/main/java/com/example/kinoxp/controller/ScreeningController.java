package com.example.kinoxp.controller;

import com.example.kinoxp.model.theatre.Screening;
import com.example.kinoxp.model.theatre.Seat;
import com.example.kinoxp.service.theatre.ScreeningService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/screenings")
@CrossOrigin(origins = "*")
public class ScreeningController {

    private final ScreeningService screeningService;

    public ScreeningController(ScreeningService screeningService) {
        this.screeningService = screeningService;
    }

    @GetMapping
    public List<Screening> getAllScreenings() {
        return screeningService.findAll();
    }

    @GetMapping("/{id}")
    public Screening getScreening(@PathVariable Integer id) {
        return screeningService.getRequiredScreening(id);
    }

    @PostMapping
    public Screening createScreening(@RequestBody Screening screening) {
        return screeningService.save(screening);
    }

    @PutMapping("/{id}")
    public Screening updateScreening(@PathVariable Integer id, @RequestBody Screening screening) {
        screening.setShowId(id);
        return screeningService.save(screening);
    }

    @DeleteMapping("/{id}")
    public void deleteScreening(@PathVariable Integer id) {
        screeningService.deleteById(id);
    }

    @GetMapping("/{id}/available-seats")
    public Set<Seat> getAvailableSeats(@PathVariable Integer id) {
        return screeningService.getAvailableSeats(id);
    }
}
