package com.zaberp.zab.biwtabackend.util;

import jakarta.persistence.PersistenceException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<String> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        // Log the full stack trace for debugging
        ex.printStackTrace();

        // Extract a meaningful message to return to the frontend
        String userFriendlyMessage = "Database constraint violation: " + ex.getMostSpecificCause().getMessage();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(userFriendlyMessage);
    }

    @ExceptionHandler(PersistenceException.class)
    public ResponseEntity<String> handlePersistenceException(PersistenceException ex) {
        ex.printStackTrace();
        String userFriendlyMessage = "An error occurred while processing your request: " + ex.getMessage();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(userFriendlyMessage);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException ex) {
        ex.printStackTrace();
        String userFriendlyMessage = "A database error occurred. Please contact support.";
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(userFriendlyMessage);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGenericException(Exception ex) {
        // Handle any unexpected exceptions
        ex.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("An unexpected error occurred. Please try again.");
    }



}
