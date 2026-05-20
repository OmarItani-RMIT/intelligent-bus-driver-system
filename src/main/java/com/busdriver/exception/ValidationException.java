package com.busdriver.exception;

/**
 * Custom exception thrown when a validation rule is violated.
 * Used throughout the system to signal invalid data inputs or constraint violations.
 */
public class ValidationException extends RuntimeException {

    public ValidationException(String message) {
        super(message);
    }

    public ValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}