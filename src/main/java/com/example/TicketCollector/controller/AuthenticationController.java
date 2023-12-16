package com.example.TicketCollector.controller;

import com.example.TicketCollector.constants.Constants;

import com.example.TicketCollector.dto.JwtRequest;
import com.example.TicketCollector.dto.ResponseDTO;
import com.example.TicketCollector.entity.UserEntity;
import com.example.TicketCollector.enums.StatusCodeEnum;
import com.example.TicketCollector.repository.UserRepository;
import com.example.TicketCollector.serviceImpl.CustomUserDetailService;
import com.example.TicketCollector.util.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Objects;
import java.util.Optional;


@RestController
@CrossOrigin
public class AuthenticationController {

    public static final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);
    @Autowired
    private AuthenticationManager authenticate;

    @Resource(name = "customUserService")
    private CustomUserDetailService userDetailsService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtUtil jwtTokenUtil;




    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseDTO createAuthenticationToken(@Valid @RequestBody JwtRequest authenticationRequest, Errors errors)
            throws Exception {
        if (errors.hasErrors()) {
            return new ResponseDTO(StatusCodeEnum.BAD_REQUEST.getStatusCode(), errors.getAllErrors().get(0).getDefaultMessage(), null);
        } else {
            try {
                authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
                logger.info("inside authenticate....");
                final UserDetails userDetails = userDetailsService
                        .loadUserByUsername(authenticationRequest.getUsername());
                final String token = jwtTokenUtil.generateTokenFromUsername(userDetails.getUsername());
                Optional<UserEntity> userEntity = userRepository.findOneByEmailIgnoreCase(authenticationRequest.getUsername());

                if (userEntity.isPresent()) {
                    UserEntity user = userEntity.get();

                    if (user.getIsVerified() == Boolean.FALSE) {
                        return new ResponseDTO("400", Constants.NOT_VERIFIED, null);
                    }
                    if (user != null && user.getIsVerified().equals(true)) {
                        return new ResponseDTO("200", Constants.LOGIN_SUCCESSFULLY, token);
                    }
                }
                return new ResponseDTO("200", Constants.LOGIN_SUCCESSFULLY, token);
            } catch (Exception e) {
                if (e.getMessage().equalsIgnoreCase(Constants.INVALID_CREDENTIALS)) {

                }
            }
            return new ResponseDTO("400", Constants.INVALID_CREDENTIALS, null);
        }
    }
    private void authenticate(String name, String password) throws Exception {
        Objects.requireNonNull(name);
        Objects.requireNonNull(password);

        try {
            authenticate.authenticate(new UsernamePasswordAuthenticationToken(name, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception(Constants.INVALID_CREDENTIALS, e);
        }
    }

}
