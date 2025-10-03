package com.example.kinoxp.model.theatre;

import com.example.kinoxp.model.employee.Employee;
import com.example.kinoxp.model.movie.Movie;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
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
    
    public void calculateEndTime() {
        if (startTime != null && movie != null && movie.getDuration() != null) {
            this.endTime = startTime.plusMinutes(movie.getDuration());
        }
    }

}