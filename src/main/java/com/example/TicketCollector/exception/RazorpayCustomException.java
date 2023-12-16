package com.example.TicketCollector.exception;

public class RazorpayCustomException extends RuntimeException{
    public RazorpayCustomException(String message) {
        super(message);
    }
}
