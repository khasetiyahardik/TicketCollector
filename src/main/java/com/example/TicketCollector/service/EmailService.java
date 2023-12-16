package com.example.TicketCollector.service;

import com.example.TicketCollector.dto.PaymentDTO;
import com.example.TicketCollector.entity.BookTicketEntity;
import com.example.TicketCollector.entity.TravellingModeEntity;
import com.example.TicketCollector.entity.UserEntity;
import com.example.TicketCollector.entity.WaitingListEntity;
import com.example.TicketCollector.helper.PdfGeneratorForTicket;
import com.razorpay.QrCode;

import javax.mail.MessagingException;

public interface EmailService {
     void sendVerifyEmail(UserEntity userEntity) throws MessagingException;
     void sendTicket(BookTicketEntity bookTicketEntity, TravellingModeEntity travellingModeEntity,String pdfPath,String QrCodeImagePath) throws MessagingException;
     void sendAvailabilityEmail(WaitingListEntity waitingListEntity);

     void sendRazorpayUpiQR(QrCode qrCode, PaymentDTO dto) throws MessagingException;
}
