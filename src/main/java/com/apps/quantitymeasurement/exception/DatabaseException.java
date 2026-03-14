package com.apps.quantitymeasurement.exception;

public class DatabaseException extends RuntimeException {

    private final String operation;

    public DatabaseException(String message, String operation) {
        super(message);
        this.operation = operation;
    }

    public DatabaseException(String message, String operation, Throwable cause) {
        super(message, cause);
        this.operation = operation;
    }

    public String getOperation() {
        return operation;
    }
}