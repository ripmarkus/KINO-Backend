package com.example.kinoxp.model.theatre;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = "seats") // FIXED: Exclude lazy-loaded collection from hashCode/equals
public class Theatre {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer theatreId;
    
    private String name;
    private Integer numRows;
    private Integer seatsPerRow;

    @OneToMany(mappedBy = "theatre")
    @JsonIgnore
    private Set<Seat> seats;
}