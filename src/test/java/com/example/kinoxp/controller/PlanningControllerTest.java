package com.example.kinoxp.controller;

import com.example.kinoxp.model.theatre.Screening;
import com.example.kinoxp.service.theatre.ScreeningService;
import com.example.kinoxp.service.movie.MovieService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PlanningControllerTest {

    @Mock
    private ScreeningService screeningService;

    @Mock
    private MovieService movieService;

    @InjectMocks
    private PlanningController planningController;

    private Screening testScreening;

    @BeforeEach
    void setUp() {
        testScreening = new Screening();
        testScreening.setShowId(1);
    }

    @Test
    void assignMovieToTheatre_ReturnsCreated() {
        when(screeningService.save(any())).thenReturn(testScreening);
        ResponseEntity<Screening> result = planningController.assignMovieToTheatre(testScreening);
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
    }

    @Test
    void getScreening_ReturnsOk() {
        when(screeningService.checkIfScreeningExists(1)).thenReturn(testScreening);
        ResponseEntity<Screening> result = planningController.getScreening(1);
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void removeAssignment_ReturnsNoContent() {
        when(screeningService.checkIfScreeningExists(1)).thenReturn(testScreening);
        ResponseEntity<Void> result = planningController.removeAssignment(1);
        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
    }

    @Test
    void getScreening_ThrowsException_WhenNotFound() {
        when(screeningService.checkIfScreeningExists(999)).thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND));
        assertThrows(ResponseStatusException.class, () -> planningController.getScreening(999));
    }
}