package com.example.kinoxp.DTO.theatre;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ScreeningRequest {
    private Integer movieId;
    private Integer theatreId;
    private LocalDateTime startTime;
    private Integer employeeId;
}

