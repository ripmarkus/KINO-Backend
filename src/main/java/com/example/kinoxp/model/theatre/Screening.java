package com.example.kinoxp.model.theatre;

import com.example.kinoxp.model.employee.Employee;
import com.example.kinoxp.model.movie.Movie;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Screening {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer showId;
    
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    
    @Enumerated(EnumType.STRING)
    private ScreeningStatus status;
    
    @ManyToOne
    private Theatre theatre;
    
    @ManyToOne
    private Movie movie;
    
    @ManyToOne
    private Employee operator;
    
    @ManyToMany
    private Set<Seat> availableSeats = new HashSet<>();
    
    @ManyToMany
    private Set<Seat> reservedSeats = new HashSet<>();
    
    public void calculateEndTime() {
        if (startTime != null && movie != null && movie.getDuration() != null) {
            this.endTime = startTime.plusMinutes(movie.getDuration());
        }
    }
    
    public boolean reserveSeats(Set<Seat> seatsToReserve) {
        if (!availableSeats.containsAll(seatsToReserve)) {
            return false;
        }
        availableSeats.removeAll(seatsToReserve);
        reservedSeats.addAll(seatsToReserve);
        return true;
    }
    
    // Helper method to initialize available seats when screening is created
    public void initializeSeats(Set<Seat> allTheatreSeats) {
        availableSeats.clear();
        availableSeats.addAll(allTheatreSeats);
        reservedSeats.clear();
    }
}