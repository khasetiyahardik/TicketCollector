package com.example.TicketCollector.exception;

public class BookingTypeMisMatchException extends RuntimeException{
    public BookingTypeMisMatchException(String message) {
        super(message);
    }
}
