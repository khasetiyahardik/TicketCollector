package com.example.TicketCollector.controller;

import static org.mockito.Mockito.when;

import com.example.TicketCollector.dto.BookTicketDTO;
import com.example.TicketCollector.dto.RegisterUserDTO;
import com.example.TicketCollector.dto.ResponseDTO;
import com.example.TicketCollector.dto.SearchDTO;
import com.example.TicketCollector.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDate;
import java.time.ZoneOffset;
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

@ContextConfiguration(classes = {UserController.class})
@ExtendWith(SpringExtension.class)
class UserControllerTest {
    @Autowired
    private UserController userController;

    @MockBean
    private UserService userService;


    @Test
    void testRegisterUser() throws Exception {
        when(userService.registerUser(Mockito.<RegisterUserDTO>any())).thenReturn(new ResponseDTO());

        RegisterUserDTO registerUserDTO = new RegisterUserDTO();
        registerUserDTO.setContactNumber("42");
        registerUserDTO.setEmail("dhruman@example.org");
        registerUserDTO.setName("Name");
        registerUserDTO.setPassword("dhruman");
        registerUserDTO.setRole("Role");
        String content = (new ObjectMapper()).writeValueAsString(registerUserDTO);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/user/registerUser")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/xml;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.content().string("<ResponseDTO><status/><message/><data/></ResponseDTO>"));
    }


    @Test
    void testGetBookingType() throws Exception {
        when(userService.getBookingType()).thenReturn(new ResponseDTO());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/user/getBookingType");
        MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/xml;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.content().string("<ResponseDTO><status/><message/><data/></ResponseDTO>"));
    }


    @Test
    void testGetTravellingMode() throws Exception {
        when(userService.getTravellingMode(Mockito.<Long>any())).thenReturn(new ResponseDTO());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/user/getTravellingMode/{bookingTypeId}", 1L);
        MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/xml;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.content().string("<ResponseDTO><status/><message/><data/></ResponseDTO>"));
    }


    @Test
    void testBookTickets() throws Exception {
        when(userService.bookTickets(Mockito.<BookTicketDTO>any(), Mockito.<Long>any())).thenReturn(new ResponseDTO());

        BookTicketDTO bookTicketDTO = new BookTicketDTO();
        bookTicketDTO.setAge(1);
        bookTicketDTO.setContactNumber("42");
        bookTicketDTO.setDestination("Destination");
        bookTicketDTO.setEmail("dhruman@example.org");
        bookTicketDTO.setGender("Male");
        bookTicketDTO.setRequiredSeats(1L);
        bookTicketDTO.setSource("Source");
        bookTicketDTO.setTicketClass("Test Class");
        bookTicketDTO.setUserName("name");
        String content = (new ObjectMapper()).writeValueAsString(bookTicketDTO);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/user/bookTickets/{travellingModeId}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/xml;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.content().string("<ResponseDTO><status/><message/><data/></ResponseDTO>"));
    }


    @Test
    void testSearch() throws Exception {
        when(userService.search(Mockito.<SearchDTO>any())).thenReturn(new ResponseDTO());

        SearchDTO searchDTO = new SearchDTO();
        searchDTO.setDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        searchDTO.setDestination("Destination");
        searchDTO.setName("Name");
        searchDTO.setSource("Source");
        String content = (new ObjectMapper()).writeValueAsString(searchDTO);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/user/search")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/xml;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.content().string("<ResponseDTO><status/><message/><data/></ResponseDTO>"));
    }


    @Test
    void testCancelBooking() throws Exception {
        when(userService.cancelBooking(Mockito.<Long>any(), Mockito.<Long>any(), Mockito.<Long>any()))
                .thenReturn(new ResponseDTO());
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders
                .get("/user/cancelBooking/{id}/{travellingModeId}", 1L, 1L);
        MockHttpServletRequestBuilder requestBuilder = getResult.param("seats", String.valueOf(1L));
        MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/xml;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.content().string("<ResponseDTO><status/><message/><data/></ResponseDTO>"));
    }
}

