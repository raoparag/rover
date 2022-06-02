package com.mars.rover.exception;

public class RoverException extends RuntimeException {
    public RoverException(String message, Throwable cause) {
        super(message, cause);
    }

    public RoverException(String message) {
        super(message);
    }
}
