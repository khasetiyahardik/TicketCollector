package com.example.TicketCollector.serviceImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.TicketCollector.dto.AddAdminDTO;
import com.example.TicketCollector.dto.ResponseDTO;
import com.example.TicketCollector.entity.UserEntity;
import com.example.TicketCollector.exception.AdminNotFoundException;
import com.example.TicketCollector.exception.EmailOrNoAlreadyExistException;
import com.example.TicketCollector.exception.InvalidUserRoleException;
import com.example.TicketCollector.repository.UserRepository;
import com.example.TicketCollector.service.EmailService;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.HashSet;
import java.util.Optional;

import javax.mail.MessagingException;

import org.junit.jupiter.api.Disabled;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {AdminServiceImpl.class})
@ExtendWith(SpringExtension.class)
class AdminServiceImplTest {
    @Autowired
    private AdminServiceImpl adminServiceImpl;

    @MockBean
    private EmailService emailService;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @MockBean
    private UserRepository userRepository;


    @Test
    void testAddAdmin() throws MessagingException {
        assertThrows(InvalidUserRoleException.class,
                () -> adminServiceImpl.addAdmin(new AddAdminDTO("Role", "Name", "42", "dhruman@example.org", "dhruman")));
    }


    @Test
    void testAddAdmin2() throws MessagingException {
        doNothing().when(emailService).sendVerifyEmail(Mockito.<UserEntity>any());

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
        when(userRepository.findByNoAndEmail(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any()))
                .thenReturn(1L);
        when(userRepository.save(Mockito.<UserEntity>any())).thenReturn(userEntity);
        assertThrows(EmailOrNoAlreadyExistException.class,
                () -> adminServiceImpl.addAdmin(new AddAdminDTO("Admin", "Name", "42", "dhruman@example.org", "dhruman")));
        verify(userRepository).findByNoAndEmail(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
    }


    @Test
    void testAddAdmin3() throws MessagingException {
        doNothing().when(emailService).sendVerifyEmail(Mockito.<UserEntity>any());
        when(userRepository.findByNoAndEmail(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any()))
                .thenThrow(new EmailOrNoAlreadyExistException("An error occurred"));
        when(userRepository.save(Mockito.<UserEntity>any()))
                .thenThrow(new EmailOrNoAlreadyExistException("An error occurred"));
        assertThrows(EmailOrNoAlreadyExistException.class,
                () -> adminServiceImpl.addAdmin(new AddAdminDTO("Admin", "Name", "42", "dhruman@example.org", "dhruman")));
        verify(userRepository).findByNoAndEmail(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
    }

    @Test
    void testAddAdmin4() throws MessagingException {
        doNothing().when(emailService).sendVerifyEmail(Mockito.<UserEntity>any());

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
        when(userRepository.findByNoAndEmail(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any()))
                .thenReturn(0L);
        when(userRepository.save(Mockito.<UserEntity>any())).thenReturn(userEntity);
        when(passwordEncoder.encode(Mockito.<CharSequence>any())).thenReturn("secret");
        ResponseDTO actualAddAdminResult = adminServiceImpl
                .addAdmin(new AddAdminDTO("Admin", "Name", "42", "dhruman@example.org", "dhruman"));
        assertNull(actualAddAdminResult.getData());
        assertEquals("200", actualAddAdminResult.getStatus());
        assertEquals("Admin added successfully", actualAddAdminResult.getMessage());
        verify(emailService).sendVerifyEmail(Mockito.<UserEntity>any());
        verify(userRepository).findByNoAndEmail(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
        verify(userRepository).save(Mockito.<UserEntity>any());
        verify(passwordEncoder).encode(Mockito.<CharSequence>any());
    }


    @Test
    void testGetAdmin() {
        when(userRepository.findByAdminId(Mockito.<Long>any())).thenReturn(Optional.empty());
        assertThrows(AdminNotFoundException.class, () -> adminServiceImpl.getAdmin(1L));
        verify(userRepository).findByAdminId(Mockito.<Long>any());
    }


    @Test
    void testGetAdmin2() {
        when(userRepository.findByAdminId(Mockito.<Long>any()))
                .thenThrow(new InvalidUserRoleException("An error occurred"));
        assertThrows(InvalidUserRoleException.class, () -> adminServiceImpl.getAdmin(1L));
        verify(userRepository).findByAdminId(Mockito.<Long>any());
    }
}

