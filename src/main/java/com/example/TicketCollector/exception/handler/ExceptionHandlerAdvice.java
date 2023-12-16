package com.example.TicketCollector.exception.handler;


import com.example.TicketCollector.dto.ErrorResponse;
import com.example.TicketCollector.dto.ResponseDTO;
import com.example.TicketCollector.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.ArrayList;

@ControllerAdvice
public class ExceptionHandlerAdvice {

    @ExceptionHandler(EmailOrNoAlreadyExistException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseDTO handleEmailOrNoAlreadyExistException(EmailOrNoAlreadyExistException exception) {
        return new ResponseDTO(String.valueOf(HttpStatus.BAD_REQUEST.value()), exception.getMessage(), null);
    }

    @ExceptionHandler(InvalidUserRoleException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseDTO handleInvalidUserRoleException(InvalidUserRoleException exception) {
        return new ResponseDTO(String.valueOf(HttpStatus.BAD_REQUEST.value()), exception.getMessage(), null);
    }

    @ExceptionHandler(CredentialsAreWrongException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseDTO handleCredentialsAreWrong(CredentialsAreWrongException exception) {
        return new ResponseDTO(String.valueOf(HttpStatus.BAD_REQUEST.value()), exception.getMessage(), null);
    }

    @ExceptionHandler(AdminNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ResponseDTO handleAdminNotFound(AdminNotFoundException exception) {
        return new ResponseDTO(String.valueOf(HttpStatus.NOT_FOUND.value()), exception.getMessage(), null);
    }

    @ExceptionHandler(BookingTypeNotEmptyException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handle(BookingTypeNotEmptyException exception) {
        return ErrorResponse.builder().errorCode(String.valueOf(HttpStatus.BAD_REQUEST))
                .errorMsg("BookingType can not be empty")
                .data(exception.getMessage()).build();
    }

    @ExceptionHandler(BookingTypeAlreadyExistException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handle(BookingTypeAlreadyExistException exception) {
        return ErrorResponse.builder().errorCode(String.valueOf(HttpStatus.BAD_REQUEST))
                .errorMsg("BookingTypeAlreadyExist can not be empty")
                .data(exception.getMessage()).build();
    }

    @ExceptionHandler(TravellingModeNotFound.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handle(TravellingModeNotFound exception) {
        return ErrorResponse.builder().errorCode(String.valueOf(HttpStatus.BAD_REQUEST))
                .errorMsg("TravellingMode Not Found")
                .data(exception.getMessage()).build();
    }

    @ExceptionHandler(BookingTypeNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handle(BookingTypeNotFoundException exception) {
        return ErrorResponse.builder().errorCode(String.valueOf(HttpStatus.BAD_REQUEST))
                .errorMsg(exception.getMessage())
                .data(new ArrayList<>()).build();
    }

    @ExceptionHandler(ClassNotAvailableForBusException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handle(ClassNotAvailableForBusException exception) {
        return ErrorResponse.builder().errorCode(String.valueOf(HttpStatus.BAD_REQUEST))
                .errorMsg(exception.getMessage())
                .data(new ArrayList<>()).build();
    }

    @ExceptionHandler(InvalidBookingTypeException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handle(InvalidBookingTypeException exception) {
        return ErrorResponse.builder().errorCode(String.valueOf(HttpStatus.BAD_REQUEST))
                .errorMsg(exception.getMessage())
                .data(new ArrayList<>()).build();
    }

    @ExceptionHandler(BookingTypeMisMatchException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handle(BookingTypeMisMatchException exception) {
        return ErrorResponse.builder().errorCode(String.valueOf(HttpStatus.BAD_REQUEST))
                .errorMsg(exception.getMessage())
                .data(new ArrayList<>()).build();
    }

    @ExceptionHandler(TravellingModeTimeExcetion.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handle(TravellingModeTimeExcetion exception) {
        return ErrorResponse.builder().errorCode(String.valueOf(HttpStatus.BAD_REQUEST))
                .errorMsg(exception.getMessage())
                .data(new ArrayList<>()).build();
    }

    @ExceptionHandler(SourceDestinetionNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handle(SourceDestinetionNotFoundException exception) {
        return ErrorResponse.builder().errorCode(String.valueOf(HttpStatus.BAD_REQUEST))
                .errorMsg(exception.getMessage())
                .data(new ArrayList<>()).build();
    }

    @ExceptionHandler(ReservationClassNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handle(ReservationClassNotFoundException exception) {
        return ErrorResponse.builder().errorCode(String.valueOf(HttpStatus.BAD_REQUEST))
                .errorMsg(exception.getMessage())
                .data(new ArrayList<>()).build();
    }

    @ExceptionHandler(InvalidBookingIdException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleInvalidBookingId(InvalidBookingIdException exception) {
        return ErrorResponse.builder().errorCode(String.valueOf(HttpStatus.BAD_REQUEST))
                .errorMsg(exception.getMessage())
                .data(new ArrayList<>()).build();
    }

    @ExceptionHandler(TicketAlreadyCancelledException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleTicketAlreadyCancelled(TicketAlreadyCancelledException exception) {
        return ErrorResponse.builder().errorCode(String.valueOf(HttpStatus.BAD_REQUEST))
                .errorMsg(exception.getMessage())
                .data(new ArrayList<>()).build();
    }

    @ExceptionHandler(CancelSeatsAreGreaterThanBookedTicketsException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handle(CancelSeatsAreGreaterThanBookedTicketsException exception) {
        return ErrorResponse.builder().errorCode(String.valueOf(HttpStatus.BAD_REQUEST))
                .errorMsg(exception.getMessage())
                .data(new ArrayList<>()).build();
    }

    @ExceptionHandler(LoginForGuestException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handle(LoginForGuestException exception) {
        return ErrorResponse.builder().errorCode(String.valueOf(HttpStatus.BAD_REQUEST))
                .errorMsg(exception.getMessage())
                .data(new ArrayList<>()).build();
    }

    @ExceptionHandler(RazorpayCustomException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handle(RazorpayCustomException exception) {
        return ErrorResponse.builder().errorCode(String.valueOf(HttpStatus.BAD_REQUEST))
                .errorMsg(exception.getMessage())
                .data(new ArrayList<>()).build();
    }
}
