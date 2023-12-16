package com.example.TicketCollector.exception;

public class InvalidBookingIdException extends RuntimeException{
    public InvalidBookingIdException(String message) {
        super(message);
    }
}
