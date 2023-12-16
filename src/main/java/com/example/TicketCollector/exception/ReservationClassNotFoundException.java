package com.example.TicketCollector.exception;

public class ReservationClassNotFoundException extends RuntimeException {
    public ReservationClassNotFoundException(String message) {
        super(message);
    }
}