package com.example.TicketCollector.service;


import com.example.TicketCollector.dto.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.razorpay.RazorpayException;

import javax.mail.MessagingException;

public interface UserService {

    ResponseDTO registerUser(RegisterUserDTO registerUserDTO) throws MessagingException;

    ResponseDTO getBookingType();

    ResponseDTO getTravellingMode(Long bookingTypeId);

    ResponseDTO bookTickets(BookTicketDTO bookTicketDTO, Long travellingModeId) throws Exception;

    ResponseDTO search(SearchDTO searchDTO);

    ResponseDTO cancelBooking(Long id, Long travellingModeId, Long seats);

    ResponseDTO initiatePayment(PaymentDTO dto) throws RazorpayException, JsonProcessingException;

    ResponseDTO initiateQRPayment(PaymentDTO dto);
}
