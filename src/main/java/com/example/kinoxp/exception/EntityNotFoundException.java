package com.example.kinoxp.exception;

public class EntityNotFoundException extends RuntimeException {
    
    public EntityNotFoundException(String message) {
        super(message);
    }
    
    public EntityNotFoundException(String entityType, Integer id) {
        super(entityType + " not found with id: " + id);
    }
    
    public EntityNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}