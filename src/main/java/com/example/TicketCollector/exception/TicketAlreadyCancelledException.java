package com.example.TicketCollector.exception;

public class TicketAlreadyCancelledException extends RuntimeException{
    public TicketAlreadyCancelledException(String message) {
        super(message);
    }
}
