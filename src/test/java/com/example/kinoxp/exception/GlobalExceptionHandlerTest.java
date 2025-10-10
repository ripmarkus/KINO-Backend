package com.example.kinoxp.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GlobalExceptionHandlerTest {

    @Mock
    private HttpServletRequest request;

    @InjectMocks
    private GlobalExceptionHandler globalExceptionHandler;

    @Test
    void handleEntityNotFoundException() {
        // Given
        EntityNotFoundException exception = new EntityNotFoundException("Customer", 999);
        when(request.getRequestURI()).thenReturn("/api/customers/999");
        when(request.getHeader("Accept")).thenReturn("application/json");

        // When
        Object result = globalExceptionHandler.handleEntityNotFoundException(exception, request);

        // Then
        assertTrue(result instanceof ResponseEntity);
        ResponseEntity<ErrorResponse> response = (ResponseEntity<ErrorResponse>) result;

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        ErrorResponse body = response.getBody();
        assertNotNull(body);
        assertEquals(404, body.getStatus());
        assertEquals("Not Found", body.getError());
        assertEquals("Customer not found with id: 999", body.getMessage());
        assertEquals("/api/customers/999", body.getPath());
        assertNotNull(body.getTimestamp());
    }


    @Test
    void handleServiceException() {
        // Given
        ServiceException exception = new ServiceException("Database connection failed");
        when(request.getRequestURI()).thenReturn("/api/customers");

        // When
        ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleServiceException(exception, request);

        // Then
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        ErrorResponse body = response.getBody();
        assertNotNull(body);
        assertEquals(500, body.getStatus());
        assertEquals("Internal Server Error", body.getError());
        assertEquals("A service error occurred: Database connection failed", body.getMessage());
        assertEquals("/api/customers", body.getPath());
    }
}