package com.example.kinoxp.DTO.booking;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateReservationRequest {

    private LocalDateTime reservationDate;
    private Boolean paid;
    private Double totalPrice;

    private Integer customerId;
    private Integer screeningId;
    private Integer salesClerkId;

    private Set<Integer> seatIds;
}