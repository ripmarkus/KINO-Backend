package com.example.kinoxp.model.booking;

import com.example.kinoxp.model.customer.Customer;
import com.example.kinoxp.model.employee.Employee;
import com.example.kinoxp.model.theatre.Screening;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;


import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = "reservationSeats") // FIXED: Exclude lazy-loaded collection from hashCode/equals
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
    @JsonIgnore
    private Set<ReservationSeat> reservationSeats;
}