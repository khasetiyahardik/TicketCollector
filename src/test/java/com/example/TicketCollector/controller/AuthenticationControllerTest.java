package com.example.TicketCollector.controller;

import static org.mockito.Mockito.when;

import com.example.TicketCollector.dto.JwtRequest;
import com.example.TicketCollector.entity.UserEntity;
import com.example.TicketCollector.repository.UserRepository;
import com.example.TicketCollector.serviceImpl.CustomUserDetailService;
import com.example.TicketCollector.util.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {AuthenticationController.class})
@ExtendWith(SpringExtension.class)
class AuthenticationControllerTest {
    @Autowired
    private AuthenticationController authenticationController;

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean(name = "customUserService")
    private CustomUserDetailService customUserDetailService;

    @MockBean
    private JwtUtil jwtUtil;

    @MockBean
    private UserRepository userRepository;


    @Test
    void testCreateAuthenticationToken() throws Exception {
        JwtRequest jwtRequest = new JwtRequest();
        jwtRequest.setPassword("dhruman");
        jwtRequest.setUsername("name");
        String content = (new ObjectMapper()).writeValueAsString(jwtRequest);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/authenticate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(authenticationController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(400));
    }


    @Test
    void testCreateAuthenticationToken2() throws Exception {
        when(jwtUtil.generateTokenFromUsername(Mockito.<String>any())).thenReturn("name");
        when(customUserDetailService.loadUserByUsername(Mockito.<String>any()))
                .thenReturn(new User("name", "dhruman", new ArrayList<>()));

        UserEntity userEntity = new UserEntity();
        userEntity.setContactNumber("42");
        userEntity.setCreatedOn(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        userEntity.setEmail("dhruman@example.org");
        userEntity.setId(1L);
        userEntity.setIsActive(true);
        userEntity.setIsVerified(true);
        userEntity.setName("Name");
        userEntity.setPassword("dhruman");
        userEntity.setRoles(new HashSet<>());
        userEntity.setUpdatedOn(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        Optional<UserEntity> ofResult = Optional.of(userEntity);
        when(userRepository.findOneByEmailIgnoreCase(Mockito.<String>any())).thenReturn(ofResult);
        when(authenticationManager.authenticate(Mockito.<Authentication>any()))
                .thenReturn(new TestingAuthenticationToken("Principal", "Credentials"));

        JwtRequest jwtRequest = new JwtRequest();
        jwtRequest.setPassword("dhruman");
        jwtRequest.setUsername("U.U.U@U.U.U.UUUU");
        String content = (new ObjectMapper()).writeValueAsString(jwtRequest);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/authenticate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(authenticationController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/xml;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("<ResponseDTO><status>200</status><message>Login successfully</message><data>name</data><"
                                + "/ResponseDTO>"));
    }


    @Test
    void testCreateAuthenticationToken3() throws Exception {
        when(jwtUtil.generateTokenFromUsername(Mockito.<String>any())).thenReturn("name");
        when(customUserDetailService.loadUserByUsername(Mockito.<String>any()))
                .thenReturn(new User("name", "dhruman", new ArrayList<>()));

        UserEntity userEntity = new UserEntity();
        userEntity.setContactNumber("42");
        userEntity.setCreatedOn(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        userEntity.setEmail("dhruman@example.org");
        userEntity.setId(1L);
        userEntity.setIsActive(true);
        userEntity.setIsVerified(true);
        userEntity.setName("Name");
        userEntity.setPassword("dhruman");
        userEntity.setRoles(new HashSet<>());
        userEntity.setUpdatedOn(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        Optional<UserEntity> ofResult = Optional.of(userEntity);
        when(userRepository.findOneByEmailIgnoreCase(Mockito.<String>any())).thenReturn(ofResult);
        when(authenticationManager.authenticate(Mockito.<Authentication>any())).thenThrow(new DisabledException("?"));

        JwtRequest jwtRequest = new JwtRequest();
        jwtRequest.setPassword("dhruman");
        jwtRequest.setUsername("U.U.U@U.U.U.UUUU");
        String content = (new ObjectMapper()).writeValueAsString(jwtRequest);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/authenticate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(authenticationController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/xml;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("<ResponseDTO><status>400</status><message>INVALID_CREDENTIALS</message><data/></ResponseDTO>"));
    }


    @Test
    void testCreateAuthenticationToken4() throws Exception {
        when(jwtUtil.generateTokenFromUsername(Mockito.<String>any())).thenReturn("name");
        when(customUserDetailService.loadUserByUsername(Mockito.<String>any()))
                .thenReturn(new User("name", "dhruman", new ArrayList<>()));

        UserEntity userEntity = new UserEntity();
        userEntity.setContactNumber("42");
        userEntity.setCreatedOn(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        userEntity.setEmail("dhruman@example.org");
        userEntity.setId(1L);
        userEntity.setIsActive(true);
        userEntity.setIsVerified(true);
        userEntity.setName("Name");
        userEntity.setPassword("dhruman");
        userEntity.setRoles(new HashSet<>());
        userEntity.setUpdatedOn(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        Optional<UserEntity> ofResult = Optional.of(userEntity);
        when(userRepository.findOneByEmailIgnoreCase(Mockito.<String>any())).thenReturn(ofResult);
        when(authenticationManager.authenticate(Mockito.<Authentication>any()))
                .thenThrow(new BadCredentialsException("?"));

        JwtRequest jwtRequest = new JwtRequest();
        jwtRequest.setPassword("dhruman");
        jwtRequest.setUsername("U.U.U@U.U.U.UUUU");
        String content = (new ObjectMapper()).writeValueAsString(jwtRequest);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/authenticate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(authenticationController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/xml;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("<ResponseDTO><status>400</status><message>INVALID_CREDENTIALS</message><data/></ResponseDTO>"));
    }
}

