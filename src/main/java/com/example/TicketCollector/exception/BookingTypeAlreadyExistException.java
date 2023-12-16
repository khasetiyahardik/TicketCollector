package com.example.TicketCollector.exception;

public class BookingTypeAlreadyExistException extends RuntimeException{
    public BookingTypeAlreadyExistException(String message) {
        super(message);
    }
}
