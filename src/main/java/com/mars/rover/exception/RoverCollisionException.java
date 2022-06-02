package com.mars.rover.exception;

public class RoverCollisionException extends RoverException {
    public RoverCollisionException(String message, Throwable cause) {
        super(message, cause);
    }

    public RoverCollisionException(String message) {
        super(message);
    }
}
