package com.example.kinoxp.model.theatre;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Theatre {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer theatreId;
    
    private String name;
    private Integer numRows;
    private Integer seatsPerRow;

    @OneToMany(mappedBy = "theater")
    private Set<Seat> seats;
}