package com.example.kinoxp.DTO.theatre;

import com.example.kinoxp.model.theatre.Seat;
import lombok.Getter;

import java.util.Set;

@Getter
public class SeatMapResponse {

    private Set<Seat> available;
    private Set<Seat> reserved;

    public SeatMapResponse(Set<Seat> available, Set<Seat> reserved) {
        this.available = available;
        this.reserved = reserved;
    }
}

