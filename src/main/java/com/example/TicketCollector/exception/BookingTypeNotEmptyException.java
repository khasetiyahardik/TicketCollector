package com.example.TicketCollector.exception;

public class BookingTypeNotEmptyException extends RuntimeException{
    public BookingTypeNotEmptyException(String message) {
        super(message);
    }
}
