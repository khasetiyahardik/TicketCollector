package com.example.TicketCollector.controller;

import static org.mockito.Mockito.when;

import com.example.TicketCollector.dto.BookingTypeDTO;
import com.example.TicketCollector.dto.ReservationClassDTO;
import com.example.TicketCollector.dto.ResponseDTO;
import com.example.TicketCollector.service.MasterService;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

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

@ContextConfiguration(classes = {MasterDataController.class})
@ExtendWith(SpringExtension.class)
class MasterDataControllerTest {
    @Autowired
    private MasterDataController masterDataController;

    @MockBean
    private MasterService masterService;

    @Test
    void testAddbookingType() throws Exception {
        when(masterService.addbookingType(Mockito.<BookingTypeDTO>any())).thenReturn(new ResponseDTO());

        BookingTypeDTO bookingTypeDTO = new BookingTypeDTO();
        bookingTypeDTO.setNames(new HashSet<>());
        String content = (new ObjectMapper()).writeValueAsString(bookingTypeDTO);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/master/addbookingType")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(masterDataController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/xml;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.content().string("<ResponseDTO><status/><message/><data/></ResponseDTO>"));
    }

    @Test
    void testEditBookingType() throws Exception {
        when(masterService.editBookingType(Mockito.<BookingTypeDTO>any(), Mockito.<Long>any()))
                .thenReturn(new ResponseDTO());

        BookingTypeDTO bookingTypeDTO = new BookingTypeDTO();
        bookingTypeDTO.setNames(new HashSet<>());
        String content = (new ObjectMapper()).writeValueAsString(bookingTypeDTO);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/master/editBookingType/{bookingTypeId}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(masterDataController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/xml;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.content().string("<ResponseDTO><status/><message/><data/></ResponseDTO>"));
    }

    @Test
    void testDeleteBookingType() throws Exception {
        when(masterService.deleteBookingType(Mockito.<Long>any())).thenReturn(new ResponseDTO());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/master/deleteBookingType/{bookingTypeId}", 1L);
        MockMvcBuilders.standaloneSetup(masterDataController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/xml;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.content().string("<ResponseDTO><status/><message/><data/></ResponseDTO>"));
    }

    @Test
    void testAddReservationClass() throws Exception {
        when(masterService.addReservationClass(Mockito.<ReservationClassDTO>any())).thenReturn(new ResponseDTO());

        ReservationClassDTO reservationClassDTO = new ReservationClassDTO();
        reservationClassDTO.setBtypeId(1L);
        reservationClassDTO.setClassName(new ArrayList<>());
        reservationClassDTO.setTmId(1L);
        String content = (new ObjectMapper()).writeValueAsString(reservationClassDTO);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/master/addReservationClass")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(masterDataController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/xml;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.content().string("<ResponseDTO><status/><message/><data/></ResponseDTO>"));
    }

    @Test
    void testSearchName() throws Exception {
        when(masterService.searchName(Mockito.<String>any())).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/master/search").param("name", "foo");
        MockMvcBuilders.standaloneSetup(masterDataController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/xml;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.content().string("<List/>"));
    }

    @Test
    void testVerify() throws Exception {
        when(masterService.verify(Mockito.<String>any(), Mockito.<String>any())).thenReturn(new ResponseDTO());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/master/verify")
                .param("email", "foo")
                .param("password", "foo");
        MockMvcBuilders.standaloneSetup(masterDataController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/xml;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.content().string("<ResponseDTO><status/><message/><data/></ResponseDTO>"));
    }

    @Test
    void testGetReservationType() throws Exception {
        when(masterService.get(Mockito.<String>any())).thenReturn(new HashMap<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/master/{fors}", "Fors");
        MockMvcBuilders.standaloneSetup(masterDataController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/xml;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.content().string("<Map/>"));
    }
}

