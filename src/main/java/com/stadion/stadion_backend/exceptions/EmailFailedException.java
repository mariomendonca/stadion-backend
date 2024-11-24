package com.stadion.stadion_backend.exceptions;

public class EmailFailedException extends RuntimeException {
    public EmailFailedException(String message) {
        super(message);
    }
}
