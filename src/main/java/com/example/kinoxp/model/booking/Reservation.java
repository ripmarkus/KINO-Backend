package com.example.kinoxp.model.booking;

import com.example.kinoxp.model.customer.Customer;
import com.example.kinoxp.model.employee.Employee;
import com.example.kinoxp.model.theatre.Screening;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;


import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer reservationId;
    
    private LocalDateTime reservationDate;
    private Boolean paid;
    private Double totalPrice;
    
    @ManyToOne
    private Customer customer;
    
    @ManyToOne
    private Screening screening;
    
    @ManyToOne
    private Employee salesClerk;

    @OneToMany(mappedBy = "reservation", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ReservationSeat> reservationSeats;
}