package com.example.TicketCollector.exception;

public class InvalidUserRoleException extends RuntimeException{
    public InvalidUserRoleException(String message) {
        super(message);
    }
}
