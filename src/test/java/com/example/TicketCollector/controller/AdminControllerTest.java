package com.example.TicketCollector.controller;

import static org.mockito.Mockito.when;

import com.example.TicketCollector.dto.AddAdminDTO;
import com.example.TicketCollector.dto.ResponseDTO;
import com.example.TicketCollector.service.AdminService;
import com.example.TicketCollector.service.TravellingModeService;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.text.ParseException;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.multipart.MultipartFile;

@ContextConfiguration(classes = {AdminController.class})
@ExtendWith(SpringExtension.class)
class AdminControllerTest {
    @Autowired
    private AdminController adminController;

    @MockBean
    private AdminService adminService;

    @MockBean
    private TravellingModeService travellingModeService;

    @Test
    void testAddAdmin() throws Exception {
        when(adminService.addAdmin(Mockito.<AddAdminDTO>any())).thenReturn(new ResponseDTO());

        AddAdminDTO addAdminDTO = new AddAdminDTO();
        addAdminDTO.setContactNumber("42");
        addAdminDTO.setEmail("dhruman@example.org");
        addAdminDTO.setName("Name");
        addAdminDTO.setPassword("dhruman");
        addAdminDTO.setRole("Role");
        String content = (new ObjectMapper()).writeValueAsString(addAdminDTO);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/admin/addAdmin")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(adminController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/xml;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.content().string("<ResponseDTO><status/><message/><data/></ResponseDTO>"));
    }

    @Test
    void testGetAdmin() throws Exception {
        when(adminService.getAdmin(Mockito.<Long>any())).thenReturn(new ResponseDTO());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/admin/getAdmin/{id}", 1L);
        MockMvcBuilders.standaloneSetup(adminController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/xml;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.content().string("<ResponseDTO><status/><message/><data/></ResponseDTO>"));
    }
}

