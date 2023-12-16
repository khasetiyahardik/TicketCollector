package com.example.TicketCollector.controller;

import static org.mockito.Mockito.when;

import com.example.TicketCollector.dto.ResponseDTO;
import com.example.TicketCollector.dto.TravellingModeDTO;
import com.example.TicketCollector.dto.TravellingSDDTO;
import com.example.TicketCollector.service.TravellingModeService;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDate;
import java.time.ZoneOffset;

import java.util.ArrayList;
import java.util.Date;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {TravellingModeController.class})
@ExtendWith(SpringExtension.class)
class TravellingModeControllerTest {
    @Autowired
    private TravellingModeController travellingModeController;

    @MockBean
    private TravellingModeService travellingModeService;


    @Test
    void testAddTm() throws Exception {
        when(travellingModeService.addTm(Mockito.<TravellingModeDTO>any())).thenReturn(new ResponseDTO());

        TravellingModeDTO travellingModeDTO = new TravellingModeDTO();
        travellingModeDTO
                .setArrivalTime(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        travellingModeDTO.setBookingTypeid(1L);
        travellingModeDTO
                .setDepartureTime(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        travellingModeDTO.setIsBus(true);
        travellingModeDTO.setIsPlane(true);
        travellingModeDTO.setIsTrain(true);
        travellingModeDTO.setReservationClass(1L);
        travellingModeDTO.setRoute(new ArrayList<>());
        travellingModeDTO.setTotalSeats(1L);
        travellingModeDTO.setTravelModeNumber("42");
        String content = (new ObjectMapper()).writeValueAsString(travellingModeDTO);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/tm/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(travellingModeController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/xml;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.content().string("<ResponseDTO><status/><message/><data/></ResponseDTO>"));
    }


    @Test
    void testAddSD() throws Exception {
        when(travellingModeService.addSD(Mockito.<Long>any(), Mockito.<TravellingSDDTO>any()))
                .thenReturn(new ResponseDTO());

        TravellingSDDTO travellingSDDTO = new TravellingSDDTO();
        travellingSDDTO.setDestination("Destination");
        travellingSDDTO.setInBetweenDestinations(new ArrayList<>());
        travellingSDDTO.setSource("Source");
        travellingSDDTO.setTotalTravellingPrice(10.0d);
        String content = (new ObjectMapper()).writeValueAsString(travellingSDDTO);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/tm/addSourceDestination/{tmId}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(travellingModeController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/xml;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.content().string("<ResponseDTO><status/><message/><data/></ResponseDTO>"));
    }
}

