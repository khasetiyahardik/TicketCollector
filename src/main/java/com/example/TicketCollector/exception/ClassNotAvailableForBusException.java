package com.example.TicketCollector.exception;

public class ClassNotAvailableForBusException extends RuntimeException{
    public ClassNotAvailableForBusException(String message) {
        super(message);
    }
}
