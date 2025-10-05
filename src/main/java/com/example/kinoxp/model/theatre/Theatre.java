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

    // CIRCULAR REFERENCE FIX: @JsonIgnore prevents infinite loop during JSON serialization
    // Without this: Theatre → Seats → Theatre → Seats → ... (infinite recursion)
    // With @JsonIgnore: Theatre JSON won't include seats collection at all
    // Use dedicated endpoints like GET /api/theatres/{id}/seats if you need seat details
    @OneToMany(mappedBy = "theatre")
    @JsonIgnore
    private Set<Seat> seats;
}