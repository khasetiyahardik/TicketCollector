package com.example.TicketCollector.exception;

public class InvalidBookingTypeException extends RuntimeException
{
    public InvalidBookingTypeException(String message) {
        super(message);
    }
}
