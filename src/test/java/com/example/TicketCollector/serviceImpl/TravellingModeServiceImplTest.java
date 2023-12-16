package com.example.TicketCollector.serviceImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.TicketCollector.dto.ResponseDTO;
import com.example.TicketCollector.dto.TravellingModeDTO;
import com.example.TicketCollector.dto.TravellingSDDTO;
import com.example.TicketCollector.entity.SDEntity;
import com.example.TicketCollector.entity.TravellingModeEntity;
import com.example.TicketCollector.entity.TravellingSDEntity;
import com.example.TicketCollector.exception.TravellingModeNotFound;
import com.example.TicketCollector.repository.BookTicketRepository;
import com.example.TicketCollector.repository.SdRepository;
import com.example.TicketCollector.repository.TravellingModeRepository;
import com.example.TicketCollector.repository.TravellingSDRepository;
import com.example.TicketCollector.service.EsService;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.text.ParseException;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.multipart.MultipartFile;

@ContextConfiguration(classes = {TravellingModeServiceImpl.class})
@ExtendWith(SpringExtension.class)
class TravellingModeServiceImplTest {
    @MockBean
    private BookTicketRepository bookTicketRepository;

    @MockBean
    private EsService esService;

    @MockBean
    private SdRepository sdRepository;

    @MockBean
    private TravellingModeRepository travellingModeRepository;

    @Autowired
    private TravellingModeServiceImpl travellingModeServiceImpl;

    @MockBean
    private TravellingSDRepository travellingSDRepository;






    @Test
    void testAddTm4() {
        TravellingModeEntity travellingModeEntity = mock(TravellingModeEntity.class);
        doNothing().when(travellingModeEntity).setArrivalTime(Mockito.<Date>any());
        doNothing().when(travellingModeEntity).setAvailableSeats(Mockito.<Long>any());
        doNothing().when(travellingModeEntity).setBookingTypeid(Mockito.<Long>any());
        doNothing().when(travellingModeEntity).setCreatedOn(Mockito.<Date>any());
        doNothing().when(travellingModeEntity).setDepartureTime(Mockito.<Date>any());
        doNothing().when(travellingModeEntity).setId(Mockito.<Long>any());
        doNothing().when(travellingModeEntity).setIntervalTimeInHour(Mockito.<Long>any());
        doNothing().when(travellingModeEntity).setIsActive(Mockito.<Boolean>any());
        doNothing().when(travellingModeEntity).setIsBus(Mockito.<Boolean>any());
        doNothing().when(travellingModeEntity).setIsPlane(Mockito.<Boolean>any());
        doNothing().when(travellingModeEntity).setIsTrain(Mockito.<Boolean>any());
        doNothing().when(travellingModeEntity).setModifiedOn(Mockito.<Date>any());
        doNothing().when(travellingModeEntity).setReservationClass(Mockito.<Long>any());
        doNothing().when(travellingModeEntity).setRoute(Mockito.<List<String>>any());
        doNothing().when(travellingModeEntity).setTotalSeats(Mockito.<Long>any());
        doNothing().when(travellingModeEntity).setTravelModeNumber(Mockito.<String>any());
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
        ArrayList<String> route = new ArrayList<>();
        travellingModeEntity.setRoute(route);
        travellingModeEntity.setTotalSeats(1L);
        travellingModeEntity.setTravelModeNumber("42");
        Optional<TravellingModeEntity> ofResult = Optional.of(travellingModeEntity);

        TravellingModeEntity travellingModeEntity2 = new TravellingModeEntity();
        travellingModeEntity2
                .setArrivalTime(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        travellingModeEntity2.setAvailableSeats(1L);
        travellingModeEntity2.setBookingTypeid(1L);
        travellingModeEntity2
                .setCreatedOn(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        travellingModeEntity2
                .setDepartureTime(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        travellingModeEntity2.setId(1L);
        travellingModeEntity2.setIntervalTimeInHour(42L);
        travellingModeEntity2.setIsActive(true);
        travellingModeEntity2.setIsBus(true);
        travellingModeEntity2.setIsPlane(true);
        travellingModeEntity2.setIsTrain(true);
        travellingModeEntity2
                .setModifiedOn(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        travellingModeEntity2.setReservationClass(1L);
        travellingModeEntity2.setRoute(new ArrayList<>());
        travellingModeEntity2.setTotalSeats(1L);
        travellingModeEntity2.setTravelModeNumber("42");
        when(travellingModeRepository.save(Mockito.<TravellingModeEntity>any())).thenReturn(travellingModeEntity2);
        when(travellingModeRepository.findByTravellingModeNumber(Mockito.<String>any())).thenReturn(ofResult);
        Date departureTime = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
        Date arrivalTime = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
        ResponseDTO actualAddTmResult = travellingModeServiceImpl.addTm(
                new TravellingModeDTO(1L, departureTime, arrivalTime, "42", new ArrayList<>(), 1L, true, true, true, 1L));
        assertEquals(route, actualAddTmResult.getData());
        assertEquals("TravellingMode updated Successfully ", actualAddTmResult.getStatus());
        assertEquals("200", actualAddTmResult.getMessage());
        verify(travellingModeRepository).save(Mockito.<TravellingModeEntity>any());
        verify(travellingModeRepository).findByTravellingModeNumber(Mockito.<String>any());
        verify(travellingModeEntity, atLeast(1)).setArrivalTime(Mockito.<Date>any());
        verify(travellingModeEntity, atLeast(1)).setAvailableSeats(Mockito.<Long>any());
        verify(travellingModeEntity, atLeast(1)).setBookingTypeid(Mockito.<Long>any());
        verify(travellingModeEntity, atLeast(1)).setCreatedOn(Mockito.<Date>any());
        verify(travellingModeEntity, atLeast(1)).setDepartureTime(Mockito.<Date>any());
        verify(travellingModeEntity).setId(Mockito.<Long>any());
        verify(travellingModeEntity, atLeast(1)).setIntervalTimeInHour(Mockito.<Long>any());
        verify(travellingModeEntity, atLeast(1)).setIsActive(Mockito.<Boolean>any());
        verify(travellingModeEntity, atLeast(1)).setIsBus(Mockito.<Boolean>any());
        verify(travellingModeEntity, atLeast(1)).setIsPlane(Mockito.<Boolean>any());
        verify(travellingModeEntity, atLeast(1)).setIsTrain(Mockito.<Boolean>any());
        verify(travellingModeEntity, atLeast(1)).setModifiedOn(Mockito.<Date>any());
        verify(travellingModeEntity, atLeast(1)).setReservationClass(Mockito.<Long>any());
        verify(travellingModeEntity, atLeast(1)).setRoute(Mockito.<List<String>>any());
        verify(travellingModeEntity, atLeast(1)).setTotalSeats(Mockito.<Long>any());
        verify(travellingModeEntity, atLeast(1)).setTravelModeNumber(Mockito.<String>any());
    }


    @Test
    void testDelete() {
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
        Optional<TravellingModeEntity> ofResult = Optional.of(travellingModeEntity);

        TravellingModeEntity travellingModeEntity2 = new TravellingModeEntity();
        travellingModeEntity2
                .setArrivalTime(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        travellingModeEntity2.setAvailableSeats(1L);
        travellingModeEntity2.setBookingTypeid(1L);
        travellingModeEntity2
                .setCreatedOn(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        travellingModeEntity2
                .setDepartureTime(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        travellingModeEntity2.setId(1L);
        travellingModeEntity2.setIntervalTimeInHour(42L);
        travellingModeEntity2.setIsActive(true);
        travellingModeEntity2.setIsBus(true);
        travellingModeEntity2.setIsPlane(true);
        travellingModeEntity2.setIsTrain(true);
        travellingModeEntity2
                .setModifiedOn(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        travellingModeEntity2.setReservationClass(1L);
        travellingModeEntity2.setRoute(new ArrayList<>());
        travellingModeEntity2.setTotalSeats(1L);
        travellingModeEntity2.setTravelModeNumber("42");
        when(travellingModeRepository.save(Mockito.<TravellingModeEntity>any())).thenReturn(travellingModeEntity2);
        when(travellingModeRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        when(bookTicketRepository.findByTmId(Mockito.<Long>any())).thenReturn(new ArrayList<>());
        ResponseDTO actualDeleteResult = travellingModeServiceImpl.delete(1L);
        assertNull(actualDeleteResult.getData());
        assertEquals("404", actualDeleteResult.getStatus());
        assertEquals("No users have booked tickets in this travelling mode", actualDeleteResult.getMessage());
        verify(travellingModeRepository).save(Mockito.<TravellingModeEntity>any());
        verify(travellingModeRepository).findById(Mockito.<Long>any());
        verify(bookTicketRepository).findByTmId(Mockito.<Long>any());
    }


    @Test
    void testDelete2() {
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
        Optional<TravellingModeEntity> ofResult = Optional.of(travellingModeEntity);

        TravellingModeEntity travellingModeEntity2 = new TravellingModeEntity();
        travellingModeEntity2
                .setArrivalTime(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        travellingModeEntity2.setAvailableSeats(1L);
        travellingModeEntity2.setBookingTypeid(1L);
        travellingModeEntity2
                .setCreatedOn(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        travellingModeEntity2
                .setDepartureTime(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        travellingModeEntity2.setId(1L);
        travellingModeEntity2.setIntervalTimeInHour(42L);
        travellingModeEntity2.setIsActive(true);
        travellingModeEntity2.setIsBus(true);
        travellingModeEntity2.setIsPlane(true);
        travellingModeEntity2.setIsTrain(true);
        travellingModeEntity2
                .setModifiedOn(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        travellingModeEntity2.setReservationClass(1L);
        travellingModeEntity2.setRoute(new ArrayList<>());
        travellingModeEntity2.setTotalSeats(1L);
        travellingModeEntity2.setTravelModeNumber("42");
        when(travellingModeRepository.save(Mockito.<TravellingModeEntity>any())).thenReturn(travellingModeEntity2);
        when(travellingModeRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        when(bookTicketRepository.findByTmId(Mockito.<Long>any()))
                .thenThrow(new TravellingModeNotFound("Not all who wander are lost"));
        assertThrows(TravellingModeNotFound.class, () -> travellingModeServiceImpl.delete(1L));
        verify(travellingModeRepository).save(Mockito.<TravellingModeEntity>any());
        verify(travellingModeRepository).findById(Mockito.<Long>any());
        verify(bookTicketRepository).findByTmId(Mockito.<Long>any());
    }


    @Test
    void testDelete3() {
        TravellingModeEntity travellingModeEntity = mock(TravellingModeEntity.class);
        doNothing().when(travellingModeEntity).setArrivalTime(Mockito.<Date>any());
        doNothing().when(travellingModeEntity).setAvailableSeats(Mockito.<Long>any());
        doNothing().when(travellingModeEntity).setBookingTypeid(Mockito.<Long>any());
        doNothing().when(travellingModeEntity).setCreatedOn(Mockito.<Date>any());
        doNothing().when(travellingModeEntity).setDepartureTime(Mockito.<Date>any());
        doNothing().when(travellingModeEntity).setId(Mockito.<Long>any());
        doNothing().when(travellingModeEntity).setIntervalTimeInHour(Mockito.<Long>any());
        doNothing().when(travellingModeEntity).setIsActive(Mockito.<Boolean>any());
        doNothing().when(travellingModeEntity).setIsBus(Mockito.<Boolean>any());
        doNothing().when(travellingModeEntity).setIsPlane(Mockito.<Boolean>any());
        doNothing().when(travellingModeEntity).setIsTrain(Mockito.<Boolean>any());
        doNothing().when(travellingModeEntity).setModifiedOn(Mockito.<Date>any());
        doNothing().when(travellingModeEntity).setReservationClass(Mockito.<Long>any());
        doNothing().when(travellingModeEntity).setRoute(Mockito.<List<String>>any());
        doNothing().when(travellingModeEntity).setTotalSeats(Mockito.<Long>any());
        doNothing().when(travellingModeEntity).setTravelModeNumber(Mockito.<String>any());
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
        Optional<TravellingModeEntity> ofResult = Optional.of(travellingModeEntity);

        TravellingModeEntity travellingModeEntity2 = new TravellingModeEntity();
        travellingModeEntity2
                .setArrivalTime(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        travellingModeEntity2.setAvailableSeats(1L);
        travellingModeEntity2.setBookingTypeid(1L);
        travellingModeEntity2
                .setCreatedOn(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        travellingModeEntity2
                .setDepartureTime(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        travellingModeEntity2.setId(1L);
        travellingModeEntity2.setIntervalTimeInHour(42L);
        travellingModeEntity2.setIsActive(true);
        travellingModeEntity2.setIsBus(true);
        travellingModeEntity2.setIsPlane(true);
        travellingModeEntity2.setIsTrain(true);
        travellingModeEntity2
                .setModifiedOn(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        travellingModeEntity2.setReservationClass(1L);
        travellingModeEntity2.setRoute(new ArrayList<>());
        travellingModeEntity2.setTotalSeats(1L);
        travellingModeEntity2.setTravelModeNumber("42");
        when(travellingModeRepository.save(Mockito.<TravellingModeEntity>any())).thenReturn(travellingModeEntity2);
        when(travellingModeRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        when(bookTicketRepository.findByTmId(Mockito.<Long>any())).thenReturn(new ArrayList<>());
        ResponseDTO actualDeleteResult = travellingModeServiceImpl.delete(1L);
        assertNull(actualDeleteResult.getData());
        assertEquals("404", actualDeleteResult.getStatus());
        assertEquals("No users have booked tickets in this travelling mode", actualDeleteResult.getMessage());
        verify(travellingModeRepository).save(Mockito.<TravellingModeEntity>any());
        verify(travellingModeRepository).findById(Mockito.<Long>any());
        verify(travellingModeEntity).setArrivalTime(Mockito.<Date>any());
        verify(travellingModeEntity).setAvailableSeats(Mockito.<Long>any());
        verify(travellingModeEntity).setBookingTypeid(Mockito.<Long>any());
        verify(travellingModeEntity).setCreatedOn(Mockito.<Date>any());
        verify(travellingModeEntity).setDepartureTime(Mockito.<Date>any());
        verify(travellingModeEntity).setId(Mockito.<Long>any());
        verify(travellingModeEntity).setIntervalTimeInHour(Mockito.<Long>any());
        verify(travellingModeEntity, atLeast(1)).setIsActive(Mockito.<Boolean>any());
        verify(travellingModeEntity).setIsBus(Mockito.<Boolean>any());
        verify(travellingModeEntity).setIsPlane(Mockito.<Boolean>any());
        verify(travellingModeEntity).setIsTrain(Mockito.<Boolean>any());
        verify(travellingModeEntity).setModifiedOn(Mockito.<Date>any());
        verify(travellingModeEntity).setReservationClass(Mockito.<Long>any());
        verify(travellingModeEntity).setRoute(Mockito.<List<String>>any());
        verify(travellingModeEntity).setTotalSeats(Mockito.<Long>any());
        verify(travellingModeEntity).setTravelModeNumber(Mockito.<String>any());
        verify(bookTicketRepository).findByTmId(Mockito.<Long>any());
    }

    @Test
    void testAddSD2() {
        when(travellingModeRepository.findById(Mockito.<Long>any())).thenReturn(Optional.empty());
        assertThrows(TravellingModeNotFound.class, () -> travellingModeServiceImpl.addSD(1L, new TravellingSDDTO()));
        verify(travellingModeRepository).findById(Mockito.<Long>any());
    }


    @Test
    void testAddSD3() {
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
        ArrayList<String> route = new ArrayList<>();
        travellingModeEntity.setRoute(route);
        travellingModeEntity.setTotalSeats(1L);
        travellingModeEntity.setTravelModeNumber("42");
        Optional<TravellingModeEntity> ofResult = Optional.of(travellingModeEntity);
        when(travellingModeRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        TravellingSDDTO travellingSDDTO = new TravellingSDDTO();
        travellingSDDTO.setInBetweenDestinations(new ArrayList<>());
        ResponseDTO actualAddSDResult = travellingModeServiceImpl.addSD(1L, travellingSDDTO);
        assertEquals(route, actualAddSDResult.getData());
        assertEquals("Travelling Source And Destination Added Successfully ", actualAddSDResult.getStatus());
        assertEquals("200", actualAddSDResult.getMessage());
        verify(travellingModeRepository).findById(Mockito.<Long>any());
    }

    @Test
    void testAddSD5() {
        when(travellingModeRepository.findById(Mockito.<Long>any()))
                .thenThrow(new TravellingModeNotFound("Not all who wander are lost"));
        assertThrows(TravellingModeNotFound.class, () -> travellingModeServiceImpl.addSD(1L, new TravellingSDDTO()));
        verify(travellingModeRepository).findById(Mockito.<Long>any());
    }

    @Test
    void testEditSD() {
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
        ArrayList<String> route = new ArrayList<>();
        travellingModeEntity.setRoute(route);
        travellingModeEntity.setTotalSeats(1L);
        travellingModeEntity.setTravelModeNumber("42");
        Optional<TravellingModeEntity> ofResult = Optional.of(travellingModeEntity);
        when(travellingModeRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        when(travellingSDRepository.findByTmId(Mockito.<Long>any())).thenReturn(new ArrayList<>());
        when(sdRepository.findByTmId(Mockito.<Long>any())).thenReturn(new ArrayList<>());
        ResponseDTO actualEditSDResult = travellingModeServiceImpl.editSD(1L, new TravellingSDDTO());
        assertEquals(route, actualEditSDResult.getData());
        assertEquals("Travelling Source And Destination EDITED Successfully ", actualEditSDResult.getStatus());
        assertEquals("200", actualEditSDResult.getMessage());
        verify(travellingModeRepository).findById(Mockito.<Long>any());
        verify(travellingSDRepository).findByTmId(Mockito.<Long>any());
        verify(sdRepository).findByTmId(Mockito.<Long>any());
    }


    @Test
    void testEditSD2() {
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
        Optional<TravellingModeEntity> ofResult = Optional.of(travellingModeEntity);
        when(travellingModeRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        when(travellingSDRepository.findByTmId(Mockito.<Long>any())).thenReturn(new ArrayList<>());
        when(sdRepository.findByTmId(Mockito.<Long>any()))
                .thenThrow(new TravellingModeNotFound("Not all who wander are lost"));
        assertThrows(TravellingModeNotFound.class, () -> travellingModeServiceImpl.editSD(1L, new TravellingSDDTO()));
        verify(travellingModeRepository).findById(Mockito.<Long>any());
        verify(sdRepository).findByTmId(Mockito.<Long>any());
    }


    @Test
    void testEditSD3() {
        TravellingModeEntity travellingModeEntity = mock(TravellingModeEntity.class);
        when(travellingModeEntity.getId()).thenReturn(1L);
        doNothing().when(travellingModeEntity).setArrivalTime(Mockito.<Date>any());
        doNothing().when(travellingModeEntity).setAvailableSeats(Mockito.<Long>any());
        doNothing().when(travellingModeEntity).setBookingTypeid(Mockito.<Long>any());
        doNothing().when(travellingModeEntity).setCreatedOn(Mockito.<Date>any());
        doNothing().when(travellingModeEntity).setDepartureTime(Mockito.<Date>any());
        doNothing().when(travellingModeEntity).setId(Mockito.<Long>any());
        doNothing().when(travellingModeEntity).setIntervalTimeInHour(Mockito.<Long>any());
        doNothing().when(travellingModeEntity).setIsActive(Mockito.<Boolean>any());
        doNothing().when(travellingModeEntity).setIsBus(Mockito.<Boolean>any());
        doNothing().when(travellingModeEntity).setIsPlane(Mockito.<Boolean>any());
        doNothing().when(travellingModeEntity).setIsTrain(Mockito.<Boolean>any());
        doNothing().when(travellingModeEntity).setModifiedOn(Mockito.<Date>any());
        doNothing().when(travellingModeEntity).setReservationClass(Mockito.<Long>any());
        doNothing().when(travellingModeEntity).setRoute(Mockito.<List<String>>any());
        doNothing().when(travellingModeEntity).setTotalSeats(Mockito.<Long>any());
        doNothing().when(travellingModeEntity).setTravelModeNumber(Mockito.<String>any());
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
        ArrayList<String> route = new ArrayList<>();
        travellingModeEntity.setRoute(route);
        travellingModeEntity.setTotalSeats(1L);
        travellingModeEntity.setTravelModeNumber("42");
        Optional<TravellingModeEntity> ofResult = Optional.of(travellingModeEntity);
        when(travellingModeRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        when(travellingSDRepository.findByTmId(Mockito.<Long>any())).thenReturn(new ArrayList<>());
        when(sdRepository.findByTmId(Mockito.<Long>any())).thenReturn(new ArrayList<>());
        ResponseDTO actualEditSDResult = travellingModeServiceImpl.editSD(1L, new TravellingSDDTO());
        assertEquals(route, actualEditSDResult.getData());
        assertEquals("Travelling Source And Destination EDITED Successfully ", actualEditSDResult.getStatus());
        assertEquals("200", actualEditSDResult.getMessage());
        verify(travellingModeRepository).findById(Mockito.<Long>any());
        verify(travellingModeEntity).getId();
        verify(travellingModeEntity).setArrivalTime(Mockito.<Date>any());
        verify(travellingModeEntity).setAvailableSeats(Mockito.<Long>any());
        verify(travellingModeEntity).setBookingTypeid(Mockito.<Long>any());
        verify(travellingModeEntity).setCreatedOn(Mockito.<Date>any());
        verify(travellingModeEntity).setDepartureTime(Mockito.<Date>any());
        verify(travellingModeEntity).setId(Mockito.<Long>any());
        verify(travellingModeEntity).setIntervalTimeInHour(Mockito.<Long>any());
        verify(travellingModeEntity).setIsActive(Mockito.<Boolean>any());
        verify(travellingModeEntity).setIsBus(Mockito.<Boolean>any());
        verify(travellingModeEntity).setIsPlane(Mockito.<Boolean>any());
        verify(travellingModeEntity).setIsTrain(Mockito.<Boolean>any());
        verify(travellingModeEntity).setModifiedOn(Mockito.<Date>any());
        verify(travellingModeEntity).setReservationClass(Mockito.<Long>any());
        verify(travellingModeEntity).setRoute(Mockito.<List<String>>any());
        verify(travellingModeEntity).setTotalSeats(Mockito.<Long>any());
        verify(travellingModeEntity).setTravelModeNumber(Mockito.<String>any());
        verify(travellingSDRepository).findByTmId(Mockito.<Long>any());
        verify(sdRepository).findByTmId(Mockito.<Long>any());
    }


    @Test
    void testEditSD4() {
        when(travellingModeRepository.findById(Mockito.<Long>any())).thenReturn(Optional.empty());
        when(travellingSDRepository.findByTmId(Mockito.<Long>any())).thenReturn(new ArrayList<>());
        when(sdRepository.findByTmId(Mockito.<Long>any())).thenReturn(new ArrayList<>());
        assertThrows(TravellingModeNotFound.class, () -> travellingModeServiceImpl.editSD(1L, new TravellingSDDTO()));
        verify(travellingModeRepository).findById(Mockito.<Long>any());
    }


    @Test
    void testEditSD5() {
        TravellingModeEntity travellingModeEntity = mock(TravellingModeEntity.class);
        when(travellingModeEntity.getId()).thenReturn(1L);
        doNothing().when(travellingModeEntity).setArrivalTime(Mockito.<Date>any());
        doNothing().when(travellingModeEntity).setAvailableSeats(Mockito.<Long>any());
        doNothing().when(travellingModeEntity).setBookingTypeid(Mockito.<Long>any());
        doNothing().when(travellingModeEntity).setCreatedOn(Mockito.<Date>any());
        doNothing().when(travellingModeEntity).setDepartureTime(Mockito.<Date>any());
        doNothing().when(travellingModeEntity).setId(Mockito.<Long>any());
        doNothing().when(travellingModeEntity).setIntervalTimeInHour(Mockito.<Long>any());
        doNothing().when(travellingModeEntity).setIsActive(Mockito.<Boolean>any());
        doNothing().when(travellingModeEntity).setIsBus(Mockito.<Boolean>any());
        doNothing().when(travellingModeEntity).setIsPlane(Mockito.<Boolean>any());
        doNothing().when(travellingModeEntity).setIsTrain(Mockito.<Boolean>any());
        doNothing().when(travellingModeEntity).setModifiedOn(Mockito.<Date>any());
        doNothing().when(travellingModeEntity).setReservationClass(Mockito.<Long>any());
        doNothing().when(travellingModeEntity).setRoute(Mockito.<List<String>>any());
        doNothing().when(travellingModeEntity).setTotalSeats(Mockito.<Long>any());
        doNothing().when(travellingModeEntity).setTravelModeNumber(Mockito.<String>any());
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
        ArrayList<String> route = new ArrayList<>();
        travellingModeEntity.setRoute(route);
        travellingModeEntity.setTotalSeats(1L);
        travellingModeEntity.setTravelModeNumber("42");
        Optional<TravellingModeEntity> ofResult = Optional.of(travellingModeEntity);
        when(travellingModeRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        SDEntity inBetweenDestinations = new SDEntity();
        inBetweenDestinations
                .setArrivalTime(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        inBetweenDestinations
                .setDepartureTime(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        inBetweenDestinations.setDestination("Travelling Source And Destination EDITED Successfully ");
        inBetweenDestinations.setPrice(10.0d);
        inBetweenDestinations.setSd_id(1L);
        inBetweenDestinations.setSource("Travelling Source And Destination EDITED Successfully ");
        inBetweenDestinations.setTmId(1L);

        TravellingSDEntity travellingSDEntity = new TravellingSDEntity();
        travellingSDEntity.setDestination("Travelling Source And Destination EDITED Successfully ");
        travellingSDEntity.setId(1L);
        travellingSDEntity.setInBetweenDestinations(inBetweenDestinations);
        travellingSDEntity.setSource("Travelling Source And Destination EDITED Successfully ");
        travellingSDEntity.setTmId(1L);

        ArrayList<TravellingSDEntity> travellingSDEntityList = new ArrayList<>();
        travellingSDEntityList.add(travellingSDEntity);
        when(travellingSDRepository.findByTmId(Mockito.<Long>any())).thenReturn(travellingSDEntityList);
        when(sdRepository.findByTmId(Mockito.<Long>any())).thenReturn(new ArrayList<>());
        ResponseDTO actualEditSDResult = travellingModeServiceImpl.editSD(1L, new TravellingSDDTO());
        assertEquals(route, actualEditSDResult.getData());
        assertEquals("Travelling Source And Destination EDITED Successfully ", actualEditSDResult.getStatus());
        assertEquals("200", actualEditSDResult.getMessage());
        verify(travellingModeRepository).findById(Mockito.<Long>any());
        verify(travellingModeEntity).getId();
        verify(travellingModeEntity).setArrivalTime(Mockito.<Date>any());
        verify(travellingModeEntity).setAvailableSeats(Mockito.<Long>any());
        verify(travellingModeEntity).setBookingTypeid(Mockito.<Long>any());
        verify(travellingModeEntity).setCreatedOn(Mockito.<Date>any());
        verify(travellingModeEntity).setDepartureTime(Mockito.<Date>any());
        verify(travellingModeEntity).setId(Mockito.<Long>any());
        verify(travellingModeEntity).setIntervalTimeInHour(Mockito.<Long>any());
        verify(travellingModeEntity).setIsActive(Mockito.<Boolean>any());
        verify(travellingModeEntity).setIsBus(Mockito.<Boolean>any());
        verify(travellingModeEntity).setIsPlane(Mockito.<Boolean>any());
        verify(travellingModeEntity).setIsTrain(Mockito.<Boolean>any());
        verify(travellingModeEntity).setModifiedOn(Mockito.<Date>any());
        verify(travellingModeEntity).setReservationClass(Mockito.<Long>any());
        verify(travellingModeEntity).setRoute(Mockito.<List<String>>any());
        verify(travellingModeEntity).setTotalSeats(Mockito.<Long>any());
        verify(travellingModeEntity).setTravelModeNumber(Mockito.<String>any());
        verify(travellingSDRepository).findByTmId(Mockito.<Long>any());
        verify(sdRepository).findByTmId(Mockito.<Long>any());
    }


    @Test
    void testEditSD6() {
        TravellingModeEntity travellingModeEntity = mock(TravellingModeEntity.class);
        when(travellingModeEntity.getId()).thenReturn(1L);
        doNothing().when(travellingModeEntity).setArrivalTime(Mockito.<Date>any());
        doNothing().when(travellingModeEntity).setAvailableSeats(Mockito.<Long>any());
        doNothing().when(travellingModeEntity).setBookingTypeid(Mockito.<Long>any());
        doNothing().when(travellingModeEntity).setCreatedOn(Mockito.<Date>any());
        doNothing().when(travellingModeEntity).setDepartureTime(Mockito.<Date>any());
        doNothing().when(travellingModeEntity).setId(Mockito.<Long>any());
        doNothing().when(travellingModeEntity).setIntervalTimeInHour(Mockito.<Long>any());
        doNothing().when(travellingModeEntity).setIsActive(Mockito.<Boolean>any());
        doNothing().when(travellingModeEntity).setIsBus(Mockito.<Boolean>any());
        doNothing().when(travellingModeEntity).setIsPlane(Mockito.<Boolean>any());
        doNothing().when(travellingModeEntity).setIsTrain(Mockito.<Boolean>any());
        doNothing().when(travellingModeEntity).setModifiedOn(Mockito.<Date>any());
        doNothing().when(travellingModeEntity).setReservationClass(Mockito.<Long>any());
        doNothing().when(travellingModeEntity).setRoute(Mockito.<List<String>>any());
        doNothing().when(travellingModeEntity).setTotalSeats(Mockito.<Long>any());
        doNothing().when(travellingModeEntity).setTravelModeNumber(Mockito.<String>any());
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
        ArrayList<String> route = new ArrayList<>();
        travellingModeEntity.setRoute(route);
        travellingModeEntity.setTotalSeats(1L);
        travellingModeEntity.setTravelModeNumber("42");
        Optional<TravellingModeEntity> ofResult = Optional.of(travellingModeEntity);
        when(travellingModeRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        SDEntity inBetweenDestinations = new SDEntity();
        inBetweenDestinations
                .setArrivalTime(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        inBetweenDestinations
                .setDepartureTime(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        inBetweenDestinations.setDestination("Travelling Source And Destination EDITED Successfully ");
        inBetweenDestinations.setPrice(10.0d);
        inBetweenDestinations.setSd_id(1L);
        inBetweenDestinations.setSource("Travelling Source And Destination EDITED Successfully ");
        inBetweenDestinations.setTmId(1L);

        TravellingSDEntity travellingSDEntity = new TravellingSDEntity();
        travellingSDEntity.setDestination("Travelling Source And Destination EDITED Successfully ");
        travellingSDEntity.setId(1L);
        travellingSDEntity.setInBetweenDestinations(inBetweenDestinations);
        travellingSDEntity.setSource("Travelling Source And Destination EDITED Successfully ");
        travellingSDEntity.setTmId(1L);

        SDEntity inBetweenDestinations2 = new SDEntity();
        inBetweenDestinations2
                .setArrivalTime(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        inBetweenDestinations2
                .setDepartureTime(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        inBetweenDestinations2.setDestination("Destination");
        inBetweenDestinations2.setPrice(0.5d);
        inBetweenDestinations2.setSd_id(2L);
        inBetweenDestinations2.setSource("Source");
        inBetweenDestinations2.setTmId(2L);

        TravellingSDEntity travellingSDEntity2 = new TravellingSDEntity();
        travellingSDEntity2.setDestination("Destination");
        travellingSDEntity2.setId(2L);
        travellingSDEntity2.setInBetweenDestinations(inBetweenDestinations2);
        travellingSDEntity2.setSource("Source");
        travellingSDEntity2.setTmId(2L);

        ArrayList<TravellingSDEntity> travellingSDEntityList = new ArrayList<>();
        travellingSDEntityList.add(travellingSDEntity2);
        travellingSDEntityList.add(travellingSDEntity);
        when(travellingSDRepository.findByTmId(Mockito.<Long>any())).thenReturn(travellingSDEntityList);
        when(sdRepository.findByTmId(Mockito.<Long>any())).thenReturn(new ArrayList<>());
        ResponseDTO actualEditSDResult = travellingModeServiceImpl.editSD(1L, new TravellingSDDTO());
        assertEquals(route, actualEditSDResult.getData());
        assertEquals("Travelling Source And Destination EDITED Successfully ", actualEditSDResult.getStatus());
        assertEquals("200", actualEditSDResult.getMessage());
        verify(travellingModeRepository).findById(Mockito.<Long>any());
        verify(travellingModeEntity).getId();
        verify(travellingModeEntity).setArrivalTime(Mockito.<Date>any());
        verify(travellingModeEntity).setAvailableSeats(Mockito.<Long>any());
        verify(travellingModeEntity).setBookingTypeid(Mockito.<Long>any());
        verify(travellingModeEntity).setCreatedOn(Mockito.<Date>any());
        verify(travellingModeEntity).setDepartureTime(Mockito.<Date>any());
        verify(travellingModeEntity).setId(Mockito.<Long>any());
        verify(travellingModeEntity).setIntervalTimeInHour(Mockito.<Long>any());
        verify(travellingModeEntity).setIsActive(Mockito.<Boolean>any());
        verify(travellingModeEntity).setIsBus(Mockito.<Boolean>any());
        verify(travellingModeEntity).setIsPlane(Mockito.<Boolean>any());
        verify(travellingModeEntity).setIsTrain(Mockito.<Boolean>any());
        verify(travellingModeEntity).setModifiedOn(Mockito.<Date>any());
        verify(travellingModeEntity).setReservationClass(Mockito.<Long>any());
        verify(travellingModeEntity).setRoute(Mockito.<List<String>>any());
        verify(travellingModeEntity).setTotalSeats(Mockito.<Long>any());
        verify(travellingModeEntity).setTravelModeNumber(Mockito.<String>any());
        verify(travellingSDRepository).findByTmId(Mockito.<Long>any());
        verify(sdRepository).findByTmId(Mockito.<Long>any());
    }


    @Test
    void testSearchName() throws JsonProcessingException {
        ArrayList<TravellingModeEntity> travellingModeEntityList = new ArrayList<>();
        when(esService.searchByTravellingModeName(Mockito.<String>any())).thenReturn(travellingModeEntityList);
        List<TravellingModeEntity> actualSearchNameResult = travellingModeServiceImpl.searchName("Name");
        assertSame(travellingModeEntityList, actualSearchNameResult);
        assertTrue(actualSearchNameResult.isEmpty());
        verify(esService).searchByTravellingModeName(Mockito.<String>any());
    }

    @Test
    void testSearchName2() throws JsonProcessingException {
        when(esService.searchByTravellingModeName(Mockito.<String>any()))
                .thenThrow(new TravellingModeNotFound("Not all who wander are lost"));
        assertThrows(TravellingModeNotFound.class, () -> travellingModeServiceImpl.searchName("Name"));
        verify(esService).searchByTravellingModeName(Mockito.<String>any());
    }



    @Test
    void testValidateArrivalAndDepartureTime3() {
        java.sql.Date arrivalTime = mock(java.sql.Date.class);
        when(arrivalTime.getTime()).thenReturn(10L);
        java.util.Date departureTime = java.util.Date
                .from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
        java.util.Date des_arrivalTime = java.util.Date
                .from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
        travellingModeServiceImpl.validateArrivalAndDepartureTime(arrivalTime, departureTime, des_arrivalTime,
                java.util.Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        verify(arrivalTime, atLeast(1)).getTime();
    }
}

