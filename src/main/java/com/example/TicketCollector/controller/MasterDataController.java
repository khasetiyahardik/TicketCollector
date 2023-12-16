package com.example.TicketCollector.controller;

import com.example.TicketCollector.dto.*;
import com.example.TicketCollector.service.MasterService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/master")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin
public class MasterDataController {

    private final MasterService masterService;

    @PostMapping("/addbookingType")
    @PreAuthorize("hasRole('ROLE_Admin')")
    public ResponseEntity<ResponseDTO> addbookingType(@RequestBody BookingTypeDTO bookingTypeDTO) {
        log.info("MasterDataController :: add() :: invoked ::");
        return new ResponseEntity<>(masterService.addbookingType(bookingTypeDTO), HttpStatus.OK);
    }

    @PutMapping("/editBookingType/{bookingTypeId}")
    @PreAuthorize("hasRole('ROLE_Admin')")
    public ResponseDTO editBookingType(@RequestBody BookingTypeDTO bookingTypeDTO, @PathVariable("bookingTypeId") Long bookingTypeId) {
        log.info("MasterDataController : editBookingType");
        return masterService.editBookingType(bookingTypeDTO, bookingTypeId);
    }

    @DeleteMapping("/deleteBookingType/{bookingTypeId}")
    @PreAuthorize("hasRole('ROLE_Admin')")
    public ResponseDTO deleteBookingType(@PathVariable("bookingTypeId") Long bookingTypeId) {
        log.info("MasterDataController : deleteBookingType");
        return masterService.deleteBookingType(bookingTypeId);
    }

    @PostMapping("/addReservationClass")
    public ResponseEntity<ResponseDTO> addReservationClass(@RequestBody ReservationClassDTO reservationClassDTO) {
        log.info("MasterDataController :: addReservationClass() :: invoked ::");
        return new ResponseEntity<>(masterService.addReservationClass(reservationClassDTO), HttpStatus.OK);
    }

    @GetMapping("/{fors}")
    @PreAuthorize("hasRole('ROLE_Admin')")
    public ResponseEntity<Map<String, List<ReservationClassResponseDTO>>> getReservationType(@PathVariable String fors) {
        log.info("MasterDataController :: getReservationType() :: invoked ::");
        return new ResponseEntity<>(masterService.get(fors), HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<EsBookingTypeInfo>> searchName(@RequestParam("name") String name) throws JsonProcessingException {
        log.info("MasterDataController : searchName");
        return new ResponseEntity<>(masterService.searchName(name), HttpStatus.OK);
    }

    @GetMapping("/verify")
    public ResponseDTO verify(@RequestParam("email") String email, @RequestParam("password") String password) {
        log.info("MasterDataController : verify");
        return masterService.verify(email, password);
    }
}
