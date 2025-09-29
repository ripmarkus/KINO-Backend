package com.example.kinoxp.model.movie;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Movie {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer movieId;
    
    private String title;
    private Integer ageLimit;
    private Integer duration;
    private String description;
    
    @ManyToOne
    private Genre genre;
}