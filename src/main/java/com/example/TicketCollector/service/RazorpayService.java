package com.example.TicketCollector.service;

import com.example.TicketCollector.dto.PaymentDTO;
import com.example.TicketCollector.dto.ResponseDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.razorpay.RazorpayException;

public interface RazorpayService {
    ResponseDTO initiatePayment(PaymentDTO dto) throws RazorpayException, JsonProcessingException;

    ResponseDTO initiateQRPayment(PaymentDTO dto);

    ResponseDTO initiateCustomer(PaymentDTO dto);
}
