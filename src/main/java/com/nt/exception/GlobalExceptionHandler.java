package com.nt.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 1. Handle Specific Exception (e.g., Booking Not Found)
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorDetails> handleRuntimeException(RuntimeException ex, WebRequest request) {
        ErrorDetails error = new ErrorDetails(
                LocalDateTime.now(), 
                ex.getMessage(), 
                request.getDescription(false)
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    // 2. Handle Data Integrity Errors (The Foreign Key error we saw earlier)
    @ExceptionHandler(org.springframework.dao.DataIntegrityViolationException.class)
    public ResponseEntity<ErrorDetails> handleDatabaseErrors(Exception ex) {
        ErrorDetails error = new ErrorDetails(
                LocalDateTime.now(),
                "Database Constraint Violation: Cannot delete record due to dependencies.",
                "FK Constraint Failed"
        );
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    // 3. Handle Global Errors (Fallback)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> handleGlobalException(Exception ex, WebRequest request) {
        ErrorDetails error = new ErrorDetails(
                LocalDateTime.now(),
                "An unexpected error occurred: " + ex.getMessage(),
                request.getDescription(false)
        );
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}