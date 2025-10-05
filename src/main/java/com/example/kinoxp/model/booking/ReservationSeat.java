package com.example.kinoxp.model.booking;

import com.example.kinoxp.model.theatre.Screening;
import com.example.kinoxp.model.theatre.Seat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(
        uniqueConstraints = {
                // FIXED: Use actual database column names instead of JPA defaults
                // Database columns: screening_show_id, seat_seat_id (not screening_id, seat_id)
                @UniqueConstraint(columnNames = {"screening_show_id", "seat_seat_id"})
        }
)
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"reservation", "screening", "seat"}) // FIXED: Exclude entities to prevent circular references
public class ReservationSeat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // FIXED: Add @JoinColumn with actual database column name
    // Database column: reservation_reservation_id (not reservation_id)
    @ManyToOne(optional = false)
    @JoinColumn(name = "reservation_reservation_id")
    private Reservation reservation;

    // FIXED: Add @JoinColumn with actual database column name  
    // Database column: screening_show_id (not screening_id)
    @ManyToOne(optional = false)
    @JoinColumn(name = "screening_show_id")
    private Screening screening;

    // FIXED: Add @JoinColumn with actual database column name
    // Database column: seat_seat_id (not seat_id)
    @ManyToOne(optional = false)
    @JoinColumn(name = "seat_seat_id")
    private Seat seat;

}
