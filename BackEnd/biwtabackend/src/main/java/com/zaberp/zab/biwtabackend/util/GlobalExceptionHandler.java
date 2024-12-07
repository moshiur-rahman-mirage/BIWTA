package com.zaberp.zab.biwtabackend.util;

import com.zaberp.zab.biwtabackend.model.ApiError;
import org.springframework.dao.DataIntegrityViolationException;
import jakarta.persistence.PersistenceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({
            DataIntegrityViolationException.class,
            PersistenceException.class,
            IllegalArgumentException.class,
            RuntimeException.class,
            Exception.class
    })
    public ResponseEntity<ApiError> handleException(Exception ex) {
        // Determine appropriate status code and message
        HttpStatus status = determineHttpStatus(ex);
        String userFriendlyMessage = generateUserFriendlyMessage(ex);
        ApiError apiError = new ApiError(userFriendlyMessage);
        return ResponseEntity.status(status).body(apiError);
    }

    private HttpStatus determineHttpStatus(Exception ex) {
        if (ex instanceof DataIntegrityViolationException || ex instanceof PersistenceException) {
            return HttpStatus.BAD_REQUEST;
        } else if (ex instanceof IllegalArgumentException) {
            return HttpStatus.BAD_REQUEST;
        } else if (ex instanceof RuntimeException) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        } else {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }

    private String generateUserFriendlyMessage(Exception ex) {
        if (ex instanceof DataIntegrityViolationException) {
            return "The operation could not be completed because some data is referenced by other records. Please resolve dependencies and try again.";
        } else if (ex instanceof PersistenceException) {
            return "A database error occurred while processing your request. Please contact support if the issue persists.";
        } else if (ex instanceof IllegalArgumentException) {
            return "Invalid input detected. Please verify your data and try again.";
        } else if (ex instanceof RuntimeException) {
            return "An unexpected error occurred. Please try again later.";
        } else {
            return "An error occurred. Please contact support if the issue persists.";
        }
    }
}
