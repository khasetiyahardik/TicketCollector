package com.example.TicketCollector.exception;

public class BookingTypeNotFoundException  extends RuntimeException{
    public BookingTypeNotFoundException(String message) {
        super(message);
    }
}
