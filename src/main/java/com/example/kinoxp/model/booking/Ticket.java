package com.example.kinoxp.model.booking;

import com.example.kinoxp.model.theatre.Seat;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ticket {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer ticketId;
    
    @ManyToOne
    private Reservation reservation;
    
    @ManyToOne
    private Seat seat;
}