package com.example.TicketCollector.service;


import com.example.TicketCollector.dto.*;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;
import java.util.Map;

public interface MasterService {

    ResponseDTO addbookingType(BookingTypeDTO bookingTypeDTO);

    ResponseDTO editBookingType(BookingTypeDTO bookingTypeDTO, Long bookingTypeId);

    ResponseDTO deleteBookingType(Long bookingTypeId);
    ResponseDTO addReservationClass(ReservationClassDTO reservationClassDTO);
    Map<String, List<ReservationClassResponseDTO>> get(String fors);

    List<EsBookingTypeInfo> searchName(String name) throws JsonProcessingException;

    ResponseDTO verify(String email, String password);
}
