package com.example.TicketCollector.serviceImpl;

import com.example.TicketCollector.constants.Constants;
import com.example.TicketCollector.dto.PaymentDTO;
import com.example.TicketCollector.dto.ResponseDTO;
import com.example.TicketCollector.entity.RazorpayPaymentEntity;
import com.example.TicketCollector.enums.RazorpayCustomerEnum;
import com.example.TicketCollector.enums.RazorpayPaymentEnum;
import com.example.TicketCollector.enums.RazorpayQREnum;
import com.example.TicketCollector.enums.StatusCodeEnum;
import com.example.TicketCollector.exception.RazorpayCustomException;
import com.example.TicketCollector.repository.RazorpayPaymentRepository;
import com.example.TicketCollector.service.EmailService;
import com.example.TicketCollector.service.RazorpayService;
import com.example.TicketCollector.util.DateUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.razorpay.*;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;


@Service
@Slf4j
public class RazorpayServiceImpl implements RazorpayService {

    @Value("${razorpay.key.id}")
    private String razorPayKeyId;

    @Value("${razorpay.key.secret}")
    private String razorPayKeySecret;

    @Autowired
    EmailService emailService;
    @Autowired
    RazorpayPaymentRepository razorpayPaymentRepository;

    @Override
    public ResponseDTO initiatePayment(PaymentDTO dto) throws RazorpayException, JsonProcessingException {
        try {
            RazorpayClient razorpayClient = new RazorpayClient(razorPayKeyId, razorPayKeySecret);
            PaymentLink payment = genratePaymentLink(razorpayClient, dto);
            generateCustomer(razorpayClient, dto);
            saveToDB(payment, dto);
            return new ResponseDTO(StatusCodeEnum.OK.getStatusCode(), Constants.PAYMENT_LINK_SENT_SUCCESSFULLY, new ArrayList<>());
        } catch (Exception e) {
            log.error("error");
            throw new RazorpayCustomException(e.getMessage());
        }
    }

    private PaymentLink genratePaymentLink(RazorpayClient razorpayClient, PaymentDTO dto) throws RazorpayException {
        JSONObject paymentLinkRequest = new JSONObject();
        paymentLinkRequest.put(RazorpayPaymentEnum.AMOUNT.getValue(), dto.getAmount() * 100);
        paymentLinkRequest.put(RazorpayPaymentEnum.CURRENCY.getValue(), dto.getCurrency());
        paymentLinkRequest.put(RazorpayPaymentEnum.ACCEPT_PARTIAL.getValue(), true);
        paymentLinkRequest.put(RazorpayPaymentEnum.FIRST_MIN_PARTIAL_AMOUNT.getValue(), 100);
        paymentLinkRequest.put(RazorpayPaymentEnum.EXPIRE_BY.getValue(), 1691097057);
        paymentLinkRequest.put(RazorpayPaymentEnum.REFERENCE_ID.getValue(), dto.getUserName() + dto.getContact());
        paymentLinkRequest.put(RazorpayPaymentEnum.DESCRIPTION.getValue(), "Payment for policy no #23456");
        JSONObject customer = new JSONObject();
        customer.put(RazorpayPaymentEnum.NAME.getValue(), dto.getUserName());
        customer.put(RazorpayPaymentEnum.CONTACT.getValue(), dto.getContact());
        customer.put(RazorpayPaymentEnum.EMAIL.getValue(), dto.getEmail());
        paymentLinkRequest.put(RazorpayPaymentEnum.CUSTOMER.getValue(), customer);
        JSONObject notify = new JSONObject();
        notify.put(RazorpayPaymentEnum.SMS.getValue(), true);
        notify.put(RazorpayPaymentEnum.EMAIL.getValue(), true);
        paymentLinkRequest.put(RazorpayPaymentEnum.NOTIFY.getValue(), notify);
        paymentLinkRequest.put(RazorpayPaymentEnum.REMINDER_ENABLE.getValue(), true);
        JSONObject notes = new JSONObject();
        notes.put(RazorpayPaymentEnum.POLICY_NAME.getValue(), RazorpayPaymentEnum.TICKET_BOOKING.getValue());
        paymentLinkRequest.put(RazorpayPaymentEnum.NOTES.getValue(), notes);
        paymentLinkRequest.put(RazorpayPaymentEnum.CALLBACK_URL.getValue(), "https://example-callback-url.com/");
        paymentLinkRequest.put(RazorpayPaymentEnum.CALLBACK_METHOD.getValue(), "get");

        PaymentLink payment = razorpayClient.paymentLink.create(paymentLinkRequest);

        return payment;
    }

    @Override
    public ResponseDTO initiateQRPayment(PaymentDTO dto) {
        try {
            RazorpayClient razorpayClient = new RazorpayClient(razorPayKeyId, razorPayKeySecret);
            QrCode qrCode = generateQRCode(razorpayClient, dto);
            emailService.sendRazorpayUpiQR(qrCode, dto);
            return new ResponseDTO(StatusCodeEnum.OK.getStatusCode(), Constants.PAYMENT_QR_SENT_SUCCESSFULLY, new ArrayList<>());
        } catch (Exception e) {
            log.error("error occurred while generating qr");
            throw new RazorpayCustomException(e.getMessage());
        }
    }

    @Override
    public ResponseDTO initiateCustomer(PaymentDTO dto) {
        try {
            RazorpayClient razorpayClient = new RazorpayClient(razorPayKeyId, razorPayKeySecret);
            generateCustomer(razorpayClient, dto);
            return new ResponseDTO(StatusCodeEnum.OK.getStatusCode(), Constants.RAZOR_PAY_CUSTOMER_CREATED, new ArrayList<>());
        } catch (Exception e) {
            log.error("error occurred while generating qr");
            throw new RazorpayCustomException(e.getMessage());
        }
    }

    private Customer generateCustomer(RazorpayClient razorpayClient, PaymentDTO dto) throws RazorpayException {
        JSONObject customerRequest = new JSONObject();
        customerRequest.put(RazorpayCustomerEnum.NAME.getValue(), dto.getUserName());
        customerRequest.put(RazorpayCustomerEnum.CONTACT.getValue(), dto.getContact());
        customerRequest.put(RazorpayCustomerEnum.EMAIL.getValue(), dto.getEmail());
        customerRequest.put(RazorpayCustomerEnum.FAILED_EXISTING.getValue(), "0");
        JSONObject notes = new JSONObject();
        notes.put(RazorpayCustomerEnum.NOTES_KEY_1.getValue(), RazorpayCustomerEnum.NA.getValue());
        customerRequest.put(RazorpayCustomerEnum.NOTES.getValue(), notes);

        Customer customer = razorpayClient.customers.create(customerRequest);

        return customer;
    }

    private QrCode generateQRCode(RazorpayClient razorpayClient, PaymentDTO dto) throws RazorpayException {
        Customer customer = generateCustomer(razorpayClient, dto);

        JSONObject qrRequest = new JSONObject();
        qrRequest.put(RazorpayQREnum.TYPE.getValue(), RazorpayQREnum.UPI_QR.getValue());
        qrRequest.put(RazorpayQREnum.NAME.getValue(), dto.getUserName());
        qrRequest.put(RazorpayQREnum.USAGE.getValue(), RazorpayQREnum.SINGLE_USE.getValue());
        qrRequest.put(RazorpayQREnum.FIXED_AMOUNT.getValue(), true);
        qrRequest.put(RazorpayQREnum.PAYMENT_AMOUNT.getValue(), dto.getAmount() * 100);
        qrRequest.put(RazorpayQREnum.DESCRIPTION.getValue(), RazorpayQREnum.NA.getValue());
        qrRequest.put(RazorpayQREnum.CUSTOMER_ID.getValue(), customer.get("id").toString());
        qrRequest.put(RazorpayQREnum.CLOSED_BY.getValue(), DateUtil.addMinIntoCurrentDate(5 * 60));
        JSONObject notes = new JSONObject();
        notes.put(RazorpayQREnum.NOTES_KEY_1.getValue(), RazorpayQREnum.NA.getValue());
        qrRequest.put(RazorpayQREnum.NOTES.getValue(), notes);

        QrCode qrcode = razorpayClient.qrCode.create(qrRequest);

        return qrcode;
    }

    private void saveToDB(PaymentLink payment, PaymentDTO dto) {
        RazorpayPaymentEntity razorpayPaymentEntity = RazorpayPaymentEntity.builder()
                .refId(payment.get("reference_id"))
                .email(dto.getEmail())
                .username(dto.getUserName())
                .paymentLinkId(payment.get("id"))
                .paymentLink(payment.get("short_url"))
                .build();
        razorpayPaymentRepository.save(razorpayPaymentEntity);
    }
}
