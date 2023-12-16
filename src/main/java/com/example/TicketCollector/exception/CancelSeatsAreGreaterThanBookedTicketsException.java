package com.example.TicketCollector.exception;

public class CancelSeatsAreGreaterThanBookedTicketsException extends RuntimeException{
    public CancelSeatsAreGreaterThanBookedTicketsException(String message) {
        super(message);
    }
}
