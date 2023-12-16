package com.example.TicketCollector.serviceImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.TicketCollector.entity.UserEntity;
import com.example.TicketCollector.repository.UserRepository;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Disabled;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {CustomUserDetailService.class})
@ExtendWith(SpringExtension.class)
class CustomUserDetailServiceTest {
    @Autowired
    private CustomUserDetailService customUserDetailService;

    @MockBean
    private UserRepository userRepository;


    @Test
    void testLoadUserByUsername() throws UsernameNotFoundException {
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
        UserDetails actualLoadUserByUsernameResult = customUserDetailService.loadUserByUsername("name");
        assertTrue(actualLoadUserByUsernameResult.getAuthorities().isEmpty());
        assertTrue(actualLoadUserByUsernameResult.isEnabled());
        assertTrue(actualLoadUserByUsernameResult.isCredentialsNonExpired());
        assertTrue(actualLoadUserByUsernameResult.isAccountNonLocked());
        assertTrue(actualLoadUserByUsernameResult.isAccountNonExpired());
        assertEquals("dhruman@example.org", actualLoadUserByUsernameResult.getUsername());
        assertEquals("dhruman", actualLoadUserByUsernameResult.getPassword());
        verify(userRepository).findOneByEmailIgnoreCase(Mockito.<String>any());
    }





    @Test
    void testLoadUserByUsername3() throws UsernameNotFoundException {
        HashSet<String> stringSet = new HashSet<>();
        stringSet.add("d");
        UserEntity userEntity = mock(UserEntity.class);
        when(userEntity.getEmail()).thenReturn("dhruman@example.org");
        when(userEntity.getPassword()).thenReturn("dhruman");
        when(userEntity.getRoles()).thenReturn(stringSet);
        doNothing().when(userEntity).setContactNumber(Mockito.<String>any());
        doNothing().when(userEntity).setCreatedOn(Mockito.<Date>any());
        doNothing().when(userEntity).setEmail(Mockito.<String>any());
        doNothing().when(userEntity).setId(Mockito.<Long>any());
        doNothing().when(userEntity).setIsActive(Mockito.<Boolean>any());
        doNothing().when(userEntity).setIsVerified(Mockito.<Boolean>any());
        doNothing().when(userEntity).setName(Mockito.<String>any());
        doNothing().when(userEntity).setPassword(Mockito.<String>any());
        doNothing().when(userEntity).setRoles(Mockito.<Set<String>>any());
        doNothing().when(userEntity).setUpdatedOn(Mockito.<Date>any());
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
        UserDetails actualLoadUserByUsernameResult = customUserDetailService.loadUserByUsername("name");
        assertEquals(1, actualLoadUserByUsernameResult.getAuthorities().size());
        assertTrue(actualLoadUserByUsernameResult.isEnabled());
        assertTrue(actualLoadUserByUsernameResult.isCredentialsNonExpired());
        assertTrue(actualLoadUserByUsernameResult.isAccountNonLocked());
        assertTrue(actualLoadUserByUsernameResult.isAccountNonExpired());
        assertEquals("dhruman@example.org", actualLoadUserByUsernameResult.getUsername());
        assertEquals("dhruman", actualLoadUserByUsernameResult.getPassword());
        verify(userRepository).findOneByEmailIgnoreCase(Mockito.<String>any());
        verify(userEntity).getEmail();
        verify(userEntity).getPassword();
        verify(userEntity).getRoles();
        verify(userEntity).setContactNumber(Mockito.<String>any());
        verify(userEntity).setCreatedOn(Mockito.<Date>any());
        verify(userEntity).setEmail(Mockito.<String>any());
        verify(userEntity).setId(Mockito.<Long>any());
        verify(userEntity).setIsActive(Mockito.<Boolean>any());
        verify(userEntity).setIsVerified(Mockito.<Boolean>any());
        verify(userEntity).setName(Mockito.<String>any());
        verify(userEntity).setPassword(Mockito.<String>any());
        verify(userEntity).setRoles(Mockito.<Set<String>>any());
        verify(userEntity).setUpdatedOn(Mockito.<Date>any());
    }


    @Test
    void testLoadUserByUsername4() throws UsernameNotFoundException {
        when(userRepository.findOneByEmailIgnoreCase(Mockito.<String>any())).thenReturn(Optional.empty());
        assertThrows(UsernameNotFoundException.class, () -> customUserDetailService.loadUserByUsername("name"));
        verify(userRepository).findOneByEmailIgnoreCase(Mockito.<String>any());
    }
}

