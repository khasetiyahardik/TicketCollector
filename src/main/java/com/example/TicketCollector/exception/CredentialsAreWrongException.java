package com.example.TicketCollector.exception;

public class CredentialsAreWrongException extends RuntimeException{
    public CredentialsAreWrongException(String message) {
        super(message);
    }
}
