package com.example.TicketCollector.exception;

public class EmailOrNoAlreadyExistException extends RuntimeException {
    public EmailOrNoAlreadyExistException(String message) {
        super(message);
    }
}
