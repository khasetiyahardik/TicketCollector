package com.example.TicketCollector.serviceImpl;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.TicketCollector.dto.EsBookingTypeInfo;
import com.example.TicketCollector.entity.TravellingModeEntity;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import org.junit.jupiter.api.Disabled;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@ContextConfiguration(classes = {EsServiceImpl.class})
@ExtendWith(SpringExtension.class)
class EsServiceImplTest {
    @Autowired
    private EsServiceImpl esServiceImpl;

    @MockBean
    private RestTemplate restTemplate;


    @Test
    void testSaveTestToElastic() throws RestClientException {
        when(restTemplate.exchange(Mockito.<String>any(), Mockito.<HttpMethod>any(), Mockito.<HttpEntity<Object>>any(),
                Mockito.<Class<Object>>any(), (Object[]) any())).thenReturn(new ResponseEntity<>(HttpStatus.CONTINUE));
        esServiceImpl.saveTestToElastic(new EsBookingTypeInfo());
        verify(restTemplate).exchange(Mockito.<String>any(), Mockito.<HttpMethod>any(), Mockito.<HttpEntity<Object>>any(),
                Mockito.<Class<Object>>any(), (Object[]) any());
    }
    
    @Test
    void testSaveTravellingModeToElastic() throws RestClientException {
        when(restTemplate.exchange(Mockito.<String>any(), Mockito.<HttpMethod>any(), Mockito.<HttpEntity<Object>>any(),
                Mockito.<Class<Object>>any(), (Object[]) any())).thenReturn(new ResponseEntity<>(HttpStatus.CONTINUE));

        TravellingModeEntity travellingModeEntity = new TravellingModeEntity();
        travellingModeEntity
                .setArrivalTime(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        travellingModeEntity.setAvailableSeats(1L);
        travellingModeEntity.setBookingTypeid(1L);
        travellingModeEntity
                .setCreatedOn(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        travellingModeEntity
                .setDepartureTime(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        travellingModeEntity.setId(1L);
        travellingModeEntity.setIntervalTimeInHour(42L);
        travellingModeEntity.setIsActive(true);
        travellingModeEntity.setIsBus(true);
        travellingModeEntity.setIsPlane(true);
        travellingModeEntity.setIsTrain(true);
        travellingModeEntity
                .setModifiedOn(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        travellingModeEntity.setReservationClass(1L);
        travellingModeEntity.setRoute(new ArrayList<>());
        travellingModeEntity.setTotalSeats(1L);
        travellingModeEntity.setTravelModeNumber("42");
        esServiceImpl.saveTravellingModeToElastic(travellingModeEntity);
        verify(restTemplate).exchange(Mockito.<String>any(), Mockito.<HttpMethod>any(), Mockito.<HttpEntity<Object>>any(),
                Mockito.<Class<Object>>any(), (Object[]) any());
    }
}

