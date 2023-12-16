package com.example.TicketCollector.serviceImpl;

import com.example.TicketCollector.dto.BookingTypeDTO;
import com.example.TicketCollector.dto.EsBookingTypeInfo;
import com.example.TicketCollector.dto.ReservationClassDTO;
import com.example.TicketCollector.dto.ResponseDTO;
import com.example.TicketCollector.entity.BookingType;
import com.example.TicketCollector.entity.ReservationClassEntity;
import com.example.TicketCollector.entity.TravellingModeEntity;
import com.example.TicketCollector.entity.UserEntity;
import com.example.TicketCollector.exception.BookingTypeNotEmptyException;
import com.example.TicketCollector.exception.BookingTypeNotFoundException;
import com.example.TicketCollector.exception.InvalidBookingTypeException;
import com.example.TicketCollector.exception.TravellingModeNotFound;
import com.example.TicketCollector.repository.BookingTypeRepository;
import com.example.TicketCollector.repository.ReservationClassRepository;
import com.example.TicketCollector.repository.TravellingModeRepository;
import com.example.TicketCollector.repository.UserRepository;
import com.example.TicketCollector.service.EsService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {MasterServiceImpl.class})
@ExtendWith(SpringExtension.class)
class MasterServiceImplTest {
    @MockBean
    private BookingTypeRepository bookingTypeRepository;

    @MockBean
    private EsService esService;

    @Autowired
    private MasterServiceImpl masterServiceImpl;

    @MockBean
    private ReservationClassRepository reservationClassRepository;

    @MockBean
    private TravellingModeRepository travellingModeRepository;

    @MockBean
    private UserRepository userRepository;

    @Test
    void testAddbookingType2() {
        assertThrows(InvalidBookingTypeException.class,
                () -> masterServiceImpl.addbookingType(new BookingTypeDTO(new HashSet<>())));
    }

    @Test
    void testEditBookingType2() {
        BookingType bookingType = new BookingType();
        bookingType.setId(1L);
        bookingType.setIsActive(true);
        bookingType.setName("Name");
        when(bookingTypeRepository.findByBookingTypeId(Mockito.<Long>any())).thenReturn(bookingType);

        BookingTypeDTO bookingTypeDTO = new BookingTypeDTO();
        bookingTypeDTO.setNames(new HashSet<>());
        ResponseDTO actualEditBookingTypeResult = masterServiceImpl.editBookingType(bookingTypeDTO, 1L);
        assertNull(actualEditBookingTypeResult.getData());
        assertEquals("200", actualEditBookingTypeResult.getStatus());
        assertEquals("Booking type updated successfully", actualEditBookingTypeResult.getMessage());
        verify(bookingTypeRepository).findByBookingTypeId(Mockito.<Long>any());
    }


    @Test
    void testEditBookingType4() {
        when(bookingTypeRepository.findByBookingTypeId(Mockito.<Long>any()))
                .thenThrow(new InvalidBookingTypeException("An error occurred"));
        assertThrows(InvalidBookingTypeException.class,
                () -> masterServiceImpl.editBookingType(new BookingTypeDTO(), 1L));
        verify(bookingTypeRepository).findByBookingTypeId(Mockito.<Long>any());
    }


    @Test
    void testEditBookingType5() {
        BookingType bookingType = new BookingType();
        bookingType.setId(1L);
        bookingType.setIsActive(true);
        bookingType.setName("Name");

        BookingType bookingType2 = new BookingType();
        bookingType2.setId(1L);
        bookingType2.setIsActive(true);
        bookingType2.setName("Name");
        when(bookingTypeRepository.save(Mockito.<BookingType>any())).thenReturn(bookingType2);
        when(bookingTypeRepository.findByBookingTypeId(Mockito.<Long>any())).thenReturn(bookingType);

        HashSet<String> names = new HashSet<>();
        names.add("MasterServiceImpl : editBookingType");

        BookingTypeDTO bookingTypeDTO = new BookingTypeDTO();
        bookingTypeDTO.setNames(names);
        ResponseDTO actualEditBookingTypeResult = masterServiceImpl.editBookingType(bookingTypeDTO, 1L);
        assertNull(actualEditBookingTypeResult.getData());
        assertEquals("200", actualEditBookingTypeResult.getStatus());
        assertEquals("Booking type updated successfully", actualEditBookingTypeResult.getMessage());
        verify(bookingTypeRepository).findByBookingTypeId(Mockito.<Long>any());
        verify(bookingTypeRepository).save(Mockito.<BookingType>any());
    }


    @Test
    void testDeleteBookingType() {
        BookingType bookingType = new BookingType();
        bookingType.setId(1L);
        bookingType.setIsActive(true);
        bookingType.setName("Name");

        BookingType bookingType2 = new BookingType();
        bookingType2.setId(1L);
        bookingType2.setIsActive(true);
        bookingType2.setName("Name");
        when(bookingTypeRepository.save(Mockito.<BookingType>any())).thenReturn(bookingType2);
        when(bookingTypeRepository.findByBookingTypeId(Mockito.<Long>any())).thenReturn(bookingType);
        ResponseDTO actualDeleteBookingTypeResult = masterServiceImpl.deleteBookingType(1L);
        assertNull(actualDeleteBookingTypeResult.getData());
        assertEquals("200", actualDeleteBookingTypeResult.getStatus());
        assertEquals("Booking type deleted successfully", actualDeleteBookingTypeResult.getMessage());
        verify(bookingTypeRepository).findByBookingTypeId(Mockito.<Long>any());
        verify(bookingTypeRepository).save(Mockito.<BookingType>any());
    }


    @Test
    void testDeleteBookingType2() {
        BookingType bookingType = new BookingType();
        bookingType.setId(1L);
        bookingType.setIsActive(true);
        bookingType.setName("Name");
        when(bookingTypeRepository.save(Mockito.<BookingType>any()))
                .thenThrow(new InvalidBookingTypeException("An error occurred"));
        when(bookingTypeRepository.findByBookingTypeId(Mockito.<Long>any())).thenReturn(bookingType);
        assertThrows(InvalidBookingTypeException.class, () -> masterServiceImpl.deleteBookingType(1L));
        verify(bookingTypeRepository).findByBookingTypeId(Mockito.<Long>any());
        verify(bookingTypeRepository).save(Mockito.<BookingType>any());
    }


    @Test
    void testDeleteBookingType3() {
        BookingType bookingType = mock(BookingType.class);
        when(bookingType.getIsActive()).thenReturn(true);
        doNothing().when(bookingType).setId(Mockito.<Long>any());
        doNothing().when(bookingType).setIsActive(Mockito.<Boolean>any());
        doNothing().when(bookingType).setName(Mockito.<String>any());
        bookingType.setId(1L);
        bookingType.setIsActive(true);
        bookingType.setName("Name");

        BookingType bookingType2 = new BookingType();
        bookingType2.setId(1L);
        bookingType2.setIsActive(true);
        bookingType2.setName("Name");
        when(bookingTypeRepository.save(Mockito.<BookingType>any())).thenReturn(bookingType2);
        when(bookingTypeRepository.findByBookingTypeId(Mockito.<Long>any())).thenReturn(bookingType);
        ResponseDTO actualDeleteBookingTypeResult = masterServiceImpl.deleteBookingType(1L);
        assertNull(actualDeleteBookingTypeResult.getData());
        assertEquals("200", actualDeleteBookingTypeResult.getStatus());
        assertEquals("Booking type deleted successfully", actualDeleteBookingTypeResult.getMessage());
        verify(bookingTypeRepository).findByBookingTypeId(Mockito.<Long>any());
        verify(bookingTypeRepository).save(Mockito.<BookingType>any());
        verify(bookingType).getIsActive();
        verify(bookingType).setId(Mockito.<Long>any());
        verify(bookingType, atLeast(1)).setIsActive(Mockito.<Boolean>any());
        verify(bookingType).setName(Mockito.<String>any());
    }


    @Test
    void testDeleteBookingType4() {
        BookingType bookingType = mock(BookingType.class);
        when(bookingType.getIsActive()).thenReturn(false);
        doNothing().when(bookingType).setId(Mockito.<Long>any());
        doNothing().when(bookingType).setIsActive(Mockito.<Boolean>any());
        doNothing().when(bookingType).setName(Mockito.<String>any());
        bookingType.setId(1L);
        bookingType.setIsActive(true);
        bookingType.setName("Name");

        BookingType bookingType2 = new BookingType();
        bookingType2.setId(1L);
        bookingType2.setIsActive(true);
        bookingType2.setName("Name");
        when(bookingTypeRepository.save(Mockito.<BookingType>any())).thenReturn(bookingType2);
        when(bookingTypeRepository.findByBookingTypeId(Mockito.<Long>any())).thenReturn(bookingType);
        ResponseDTO actualDeleteBookingTypeResult = masterServiceImpl.deleteBookingType(1L);
        assertNull(actualDeleteBookingTypeResult.getData());
        assertEquals("400", actualDeleteBookingTypeResult.getStatus());
        assertEquals("Booking type already deleted", actualDeleteBookingTypeResult.getMessage());
        verify(bookingTypeRepository).findByBookingTypeId(Mockito.<Long>any());
        verify(bookingType).getIsActive();
        verify(bookingType).setId(Mockito.<Long>any());
        verify(bookingType).setIsActive(Mockito.<Boolean>any());
        verify(bookingType).setName(Mockito.<String>any());
    }


    @Test
    void testSearchName() throws JsonProcessingException {
        ArrayList<EsBookingTypeInfo> esBookingTypeInfoList = new ArrayList<>();
        when(esService.searchByBookingTypeName(Mockito.<String>any())).thenReturn(esBookingTypeInfoList);
        List<EsBookingTypeInfo> actualSearchNameResult = masterServiceImpl.searchName("Name");
        assertSame(esBookingTypeInfoList, actualSearchNameResult);
        assertTrue(actualSearchNameResult.isEmpty());
        verify(esService).searchByBookingTypeName(Mockito.<String>any());
    }


    @Test
    void testSearchName2() throws JsonProcessingException {
        when(esService.searchByBookingTypeName(Mockito.<String>any()))
                .thenThrow(new InvalidBookingTypeException("An error occurred"));
        assertThrows(InvalidBookingTypeException.class, () -> masterServiceImpl.searchName("Name"));
        verify(esService).searchByBookingTypeName(Mockito.<String>any());
    }


    @Test
    void testVerify() {
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
        when(userRepository.findByNameAndPassword(Mockito.<String>any(), Mockito.<String>any())).thenReturn(userEntity);
        ResponseDTO actualVerifyResult = masterServiceImpl.verify("dhruman@example.org", "dhruman");
        assertNull(actualVerifyResult.getData());
        assertEquals("400", actualVerifyResult.getStatus());
        assertEquals("Already verified", actualVerifyResult.getMessage());
        verify(userRepository).findByNameAndPassword(Mockito.<String>any(), Mockito.<String>any());
    }


    @Test
    void testVerify2() {
        UserEntity userEntity = mock(UserEntity.class);
        when(userEntity.getIsVerified()).thenReturn(false);
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

        UserEntity userEntity2 = new UserEntity();
        userEntity2.setContactNumber("42");
        userEntity2.setCreatedOn(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        userEntity2.setEmail("dhruman@example.org");
        userEntity2.setId(1L);
        userEntity2.setIsActive(true);
        userEntity2.setIsVerified(true);
        userEntity2.setName("Name");
        userEntity2.setPassword("dhruman");
        userEntity2.setRoles(new HashSet<>());
        userEntity2.setUpdatedOn(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        when(userRepository.save(Mockito.<UserEntity>any())).thenReturn(userEntity2);
        when(userRepository.findByNameAndPassword(Mockito.<String>any(), Mockito.<String>any())).thenReturn(userEntity);
        ResponseDTO actualVerifyResult = masterServiceImpl.verify("dhruman@example.org", "dhruman");
        assertNull(actualVerifyResult.getData());
        assertEquals("200", actualVerifyResult.getStatus());
        assertEquals("Verified successfully", actualVerifyResult.getMessage());
        verify(userRepository).findByNameAndPassword(Mockito.<String>any(), Mockito.<String>any());
        verify(userRepository).save(Mockito.<UserEntity>any());
        verify(userEntity).getIsVerified();
        verify(userEntity).setContactNumber(Mockito.<String>any());
        verify(userEntity).setCreatedOn(Mockito.<Date>any());
        verify(userEntity).setEmail(Mockito.<String>any());
        verify(userEntity).setId(Mockito.<Long>any());
        verify(userEntity).setIsActive(Mockito.<Boolean>any());
        verify(userEntity, atLeast(1)).setIsVerified(Mockito.<Boolean>any());
        verify(userEntity).setName(Mockito.<String>any());
        verify(userEntity).setPassword(Mockito.<String>any());
        verify(userEntity).setRoles(Mockito.<Set<String>>any());
        verify(userEntity).setUpdatedOn(Mockito.<Date>any());
    }

    @Test
    void testAddReservationClass2() {
        when(bookingTypeRepository.findById(Mockito.<Long>any()))
                .thenThrow(new InvalidBookingTypeException("An error occurred"));

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
        assertThrows(InvalidBookingTypeException.class,
                () -> masterServiceImpl.addReservationClass(new ReservationClassDTO()));
        verify(bookingTypeRepository).findById(Mockito.<Long>any());
        verify(travellingModeRepository).findById(Mockito.<Long>any());
    }

    @Test
    void testAddReservationClass4() {
        when(bookingTypeRepository.findById(Mockito.<Long>any())).thenReturn(Optional.empty());

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
        assertThrows(BookingTypeNotFoundException.class,
                () -> masterServiceImpl.addReservationClass(new ReservationClassDTO()));
        verify(bookingTypeRepository).findById(Mockito.<Long>any());
        verify(travellingModeRepository).findById(Mockito.<Long>any());
    }


    @Test
    void testAddReservationClass5() {
        BookingType bookingType = mock(BookingType.class);
        when(bookingType.getId()).thenReturn(1L);
        doNothing().when(bookingType).setId(Mockito.<Long>any());
        doNothing().when(bookingType).setIsActive(Mockito.<Boolean>any());
        doNothing().when(bookingType).setName(Mockito.<String>any());
        bookingType.setId(1L);
        bookingType.setIsActive(true);
        bookingType.setName("Name");
        Optional<BookingType> ofResult = Optional.of(bookingType);
        when(bookingTypeRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        when(travellingModeRepository.findById(Mockito.<Long>any())).thenReturn(Optional.empty());
        assertThrows(TravellingModeNotFound.class,
                () -> masterServiceImpl.addReservationClass(new ReservationClassDTO()));
        verify(bookingType).setId(Mockito.<Long>any());
        verify(bookingType).setIsActive(Mockito.<Boolean>any());
        verify(bookingType).setName(Mockito.<String>any());
        verify(travellingModeRepository).findById(Mockito.<Long>any());
    }


    @Test
    void testAddReservationClass6() {
        BookingType bookingType = mock(BookingType.class);
        when(bookingType.getId()).thenReturn(1L);
        doNothing().when(bookingType).setId(Mockito.<Long>any());
        doNothing().when(bookingType).setIsActive(Mockito.<Boolean>any());
        doNothing().when(bookingType).setName(Mockito.<String>any());
        bookingType.setId(1L);
        bookingType.setIsActive(true);
        bookingType.setName("Name");
        Optional<BookingType> ofResult = Optional.of(bookingType);
        when(bookingTypeRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        TravellingModeEntity travellingModeEntity = mock(TravellingModeEntity.class);
        when(travellingModeEntity.getBookingTypeid()).thenReturn(1L);
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
        Optional<TravellingModeEntity> ofResult2 = Optional.of(travellingModeEntity);
        when(travellingModeRepository.findById(Mockito.<Long>any())).thenReturn(ofResult2);
        ResponseDTO actualAddReservationClassResult = masterServiceImpl
                .addReservationClass(new ReservationClassDTO(1L, new ArrayList<>(), 1L));
        assertEquals(route, actualAddReservationClassResult.getData());
        assertEquals("Reservation class Added Successfully ", actualAddReservationClassResult.getStatus());
        assertEquals("200", actualAddReservationClassResult.getMessage());
        verify(bookingTypeRepository).findById(Mockito.<Long>any());
        verify(bookingType).getId();
        verify(bookingType).setId(Mockito.<Long>any());
        verify(bookingType).setIsActive(Mockito.<Boolean>any());
        verify(bookingType).setName(Mockito.<String>any());
        verify(travellingModeRepository).findById(Mockito.<Long>any());
        verify(travellingModeEntity).getBookingTypeid();
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
    }

    @Test
    void testGet() {
        when(reservationClassRepository.findPlaneClass()).thenReturn(new ArrayList<>());
        when(reservationClassRepository.findTrainClass()).thenReturn(new ArrayList<>());
        assertEquals(1, masterServiceImpl.get("Fors").size());
        verify(reservationClassRepository).findPlaneClass();
        verify(reservationClassRepository).findTrainClass();
    }


    @Test
    void testGet2() {
        when(reservationClassRepository.findPlaneClass())
                .thenThrow(new BookingTypeNotEmptyException("An error occurred"));
        when(reservationClassRepository.findTrainClass())
                .thenThrow(new BookingTypeNotEmptyException("An error occurred"));
        assertThrows(BookingTypeNotEmptyException.class, () -> masterServiceImpl.get("Fors"));
        verify(reservationClassRepository).findPlaneClass();
    }


    @Test
    void testGet3() {
        when(reservationClassRepository.findPlaneClass()).thenReturn(new ArrayList<>());
        when(reservationClassRepository.findTrainClass()).thenReturn(new ArrayList<>());
        assertEquals(1, masterServiceImpl.get("plane").size());
        verify(reservationClassRepository).findPlaneClass();
        verify(reservationClassRepository).findTrainClass();
    }


    @Test
    void testGet4() {
        when(reservationClassRepository.findPlaneClass()).thenReturn(new ArrayList<>());
        when(reservationClassRepository.findTrainClass()).thenReturn(new ArrayList<>());
        assertEquals(1, masterServiceImpl.get("train").size());
        verify(reservationClassRepository).findPlaneClass();
        verify(reservationClassRepository).findTrainClass();
    }


    @Test
    void testGet5() {
        ReservationClassEntity reservationClassEntity = new ReservationClassEntity();
        reservationClassEntity.setClassName("42");
        reservationClassEntity.setId(2L);
        reservationClassEntity.setIsFlight(false);
        reservationClassEntity.setIsTrain(false);
        reservationClassEntity.setRate(0.5d);
        reservationClassEntity.setTmId(2L);

        ArrayList<ReservationClassEntity> reservationClassEntityList = new ArrayList<>();
        reservationClassEntityList.add(reservationClassEntity);
        when(reservationClassRepository.findPlaneClass()).thenReturn(reservationClassEntityList);
        when(reservationClassRepository.findTrainClass()).thenReturn(new ArrayList<>());
        assertEquals(1, masterServiceImpl.get("plane").size());
        verify(reservationClassRepository).findPlaneClass();
        verify(reservationClassRepository).findTrainClass();
    }


    @Test
    void testGet6() {
        ReservationClassEntity reservationClassEntity = new ReservationClassEntity();
        reservationClassEntity.setClassName("42");
        reservationClassEntity.setId(2L);
        reservationClassEntity.setIsFlight(false);
        reservationClassEntity.setIsTrain(false);
        reservationClassEntity.setRate(0.5d);
        reservationClassEntity.setTmId(2L);

        ReservationClassEntity reservationClassEntity2 = new ReservationClassEntity();
        reservationClassEntity2.setClassName("");
        reservationClassEntity2.setId(3L);
        reservationClassEntity2.setIsFlight(true);
        reservationClassEntity2.setIsTrain(true);
        reservationClassEntity2.setRate(-0.5d);
        reservationClassEntity2.setTmId(3L);

        ArrayList<ReservationClassEntity> reservationClassEntityList = new ArrayList<>();
        reservationClassEntityList.add(reservationClassEntity2);
        reservationClassEntityList.add(reservationClassEntity);
        when(reservationClassRepository.findPlaneClass()).thenReturn(reservationClassEntityList);
        when(reservationClassRepository.findTrainClass()).thenReturn(new ArrayList<>());
        assertEquals(1, masterServiceImpl.get("plane").size());
        verify(reservationClassRepository).findPlaneClass();
        verify(reservationClassRepository).findTrainClass();
    }


    @Test
    void testGet7() {
        ReservationClassEntity reservationClassEntity = new ReservationClassEntity();
        reservationClassEntity.setClassName("");
        reservationClassEntity.setId(3L);
        reservationClassEntity.setIsFlight(true);
        reservationClassEntity.setIsTrain(true);
        reservationClassEntity.setRate(-0.5d);
        reservationClassEntity.setTmId(3L);

        ArrayList<ReservationClassEntity> reservationClassEntityList = new ArrayList<>();
        reservationClassEntityList.add(reservationClassEntity);
        when(reservationClassRepository.findPlaneClass()).thenReturn(new ArrayList<>());
        when(reservationClassRepository.findTrainClass()).thenReturn(reservationClassEntityList);
        assertEquals(1, masterServiceImpl.get("train").size());
        verify(reservationClassRepository).findPlaneClass();
        verify(reservationClassRepository).findTrainClass();
    }


    @Test
    void testGet8() {
        ReservationClassEntity reservationClassEntity = new ReservationClassEntity();
        reservationClassEntity.setClassName("");
        reservationClassEntity.setId(3L);
        reservationClassEntity.setIsFlight(true);
        reservationClassEntity.setIsTrain(true);
        reservationClassEntity.setRate(-0.5d);
        reservationClassEntity.setTmId(3L);

        ReservationClassEntity reservationClassEntity2 = new ReservationClassEntity();
        reservationClassEntity2.setClassName("Class Name");
        reservationClassEntity2.setId(1L);
        reservationClassEntity2.setIsFlight(false);
        reservationClassEntity2.setIsTrain(false);
        reservationClassEntity2.setRate(10.0d);
        reservationClassEntity2.setTmId(1L);

        ArrayList<ReservationClassEntity> reservationClassEntityList = new ArrayList<>();
        reservationClassEntityList.add(reservationClassEntity2);
        reservationClassEntityList.add(reservationClassEntity);
        when(reservationClassRepository.findPlaneClass()).thenReturn(new ArrayList<>());
        when(reservationClassRepository.findTrainClass()).thenReturn(reservationClassEntityList);
        assertEquals(1, masterServiceImpl.get("train").size());
        verify(reservationClassRepository).findPlaneClass();
        verify(reservationClassRepository).findTrainClass();
    }


    @Test
    void testGet9() {
        ReservationClassEntity reservationClassEntity = mock(ReservationClassEntity.class);
        when(reservationClassEntity.getId()).thenReturn(1L);
        when(reservationClassEntity.getClassName()).thenReturn("Class Name");
        doNothing().when(reservationClassEntity).setClassName(Mockito.<String>any());
        doNothing().when(reservationClassEntity).setId(Mockito.<Long>any());
        doNothing().when(reservationClassEntity).setIsFlight(Mockito.<Boolean>any());
        doNothing().when(reservationClassEntity).setIsTrain(Mockito.<Boolean>any());
        doNothing().when(reservationClassEntity).setRate(Mockito.<Double>any());
        doNothing().when(reservationClassEntity).setTmId(Mockito.<Long>any());
        reservationClassEntity.setClassName("42");
        reservationClassEntity.setId(2L);
        reservationClassEntity.setIsFlight(false);
        reservationClassEntity.setIsTrain(false);
        reservationClassEntity.setRate(0.5d);
        reservationClassEntity.setTmId(2L);

        ArrayList<ReservationClassEntity> reservationClassEntityList = new ArrayList<>();
        reservationClassEntityList.add(reservationClassEntity);
        when(reservationClassRepository.findPlaneClass()).thenReturn(reservationClassEntityList);
        when(reservationClassRepository.findTrainClass()).thenReturn(new ArrayList<>());
        assertEquals(1, masterServiceImpl.get("plane").size());
        verify(reservationClassRepository).findPlaneClass();
        verify(reservationClassRepository).findTrainClass();
        verify(reservationClassEntity).getId();
        verify(reservationClassEntity).getClassName();
        verify(reservationClassEntity).setClassName(Mockito.<String>any());
        verify(reservationClassEntity).setId(Mockito.<Long>any());
        verify(reservationClassEntity).setIsFlight(Mockito.<Boolean>any());
        verify(reservationClassEntity).setIsTrain(Mockito.<Boolean>any());
        verify(reservationClassEntity).setRate(Mockito.<Double>any());
        verify(reservationClassEntity).setTmId(Mockito.<Long>any());
    }
}

