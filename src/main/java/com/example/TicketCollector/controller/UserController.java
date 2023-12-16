package com.example.TicketCollector.controller;


import com.example.TicketCollector.dto.*;
import com.example.TicketCollector.enums.StatusCodeEnum;
import com.example.TicketCollector.service.CloudinaryService;
import com.example.TicketCollector.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.razorpay.RazorpayException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {


    @Autowired
    UserService userService;

    @Autowired
    CloudinaryService cloudinaryService;

    @PostMapping("/registerUser")
    public ResponseDTO registerUser(@RequestBody RegisterUserDTO registerUserDTO) throws MessagingException {
        log.info("UserController : registerUser");
        return userService.registerUser(registerUserDTO);
    }

    @GetMapping("/getBookingType")
    public ResponseDTO getBookingType() {
        log.info("UserController : getBookingType");
        return userService.getBookingType();
    }

    @GetMapping("/getTravellingMode/{bookingTypeId}")
    public ResponseDTO getTravellingMode(@PathVariable("bookingTypeId") Long bookingTypeId) {
        log.info("UserController : getTravellingMode");
        return userService.getTravellingMode(bookingTypeId);
    }

    @PostMapping("/bookTickets/{travellingModeId}")
    public ResponseDTO bookTickets(@Valid @RequestBody BookTicketDTO bookTicketDTO, @PathVariable("travellingModeId") Long travellingModeId, Errors errors) throws Exception {
        log.info("UserController : bookTickets");
        if (errors.hasErrors()) {
            return new ResponseDTO(StatusCodeEnum.BAD_REQUEST.getStatusCode(), errors.getAllErrors().get(0).getDefaultMessage(), null);
        } else {
            return userService.bookTickets(bookTicketDTO, travellingModeId);
        }
    }

    @PostMapping("/search")
    public ResponseDTO search(@Valid @RequestBody SearchDTO searchDTO, Errors errors) {
        if (errors.hasErrors()) {
            return new ResponseDTO(StatusCodeEnum.BAD_REQUEST.getStatusCode(), errors.getAllErrors().get(0).getDefaultMessage(), null);
        } else {
            return userService.search(searchDTO);
        }
    }

    @GetMapping("/cancelBooking/{id}/{travellingModeId}")
    public ResponseDTO cancelBooking(@PathVariable("id") Long id, @PathVariable("travellingModeId") Long travellingModeId, @RequestParam("seats") Long seats) {
        log.info("UserController : cancelBooking");
        return userService.cancelBooking(id, travellingModeId, seats);
    }

    @PostMapping("/uploadFile")
    public ResponseDTO uploadFile(@RequestParam MultipartFile file) throws IOException {
        String url = cloudinaryService.uploadFileOnCloudinary(file.getBytes());
        log.info("url " + url);
        return new ResponseDTO("200", "uploaded", new ArrayList<>());
    }

    @PostMapping("/initiate/payment")
    public ResponseDTO initiatePayment(@RequestBody PaymentDTO dto) throws RazorpayException, JsonProcessingException {
        return userService.initiatePayment(dto);
    }

    @PostMapping("/initiate/qr/payment")
    public ResponseDTO initiateQRPayment(@RequestBody PaymentDTO dto) throws RazorpayException, JsonProcessingException {
        return userService.initiateQRPayment(dto);
    }
}
