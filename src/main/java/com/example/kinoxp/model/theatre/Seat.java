package com.example.kinoxp.model.theatre;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Seat {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer seatId;
    
    @Column(name = "seat_row")
    private Integer rowNumber;
    private Integer seatNumber;
    
    @ManyToOne
    private Theatre theatre;
}