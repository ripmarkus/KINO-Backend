package com.example.kinoxp.model.theatre;

import com.example.kinoxp.model.employee.Employee;
import com.example.kinoxp.model.movie.Movie;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Screening {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer showId;
    
    private LocalDateTime dateTime;
    
    @Enumerated(EnumType.STRING)
    private ScreeningStatus status;
    
    @ManyToOne
    private Theatre theatre;
    
    @ManyToOne
    private Movie movie;
    
    @ManyToOne
    private Employee operator;
}