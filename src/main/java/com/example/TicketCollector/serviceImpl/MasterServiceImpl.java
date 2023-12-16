package com.example.TicketCollector.serviceImpl;

import com.example.TicketCollector.constants.Constants;

import com.example.TicketCollector.dto.*;
import com.example.TicketCollector.entity.BookingType;
import com.example.TicketCollector.entity.ReservationClassEntity;
import com.example.TicketCollector.entity.TravellingModeEntity;
import com.example.TicketCollector.entity.UserEntity;
import com.example.TicketCollector.enums.BookingTypeEnums;
import com.example.TicketCollector.enums.StatusCodeEnum;
import com.example.TicketCollector.exception.*;
import com.example.TicketCollector.repository.BookingTypeRepository;
import com.example.TicketCollector.repository.ReservationClassRepository;
import com.example.TicketCollector.repository.TravellingModeRepository;
import com.example.TicketCollector.repository.UserRepository;
import com.example.TicketCollector.service.EsService;
import com.example.TicketCollector.service.MasterService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class MasterServiceImpl implements MasterService {

    public static final Logger logger = LoggerFactory.getLogger(MasterServiceImpl.class);

    private final BookingTypeRepository bookingTypeRepository;
    private final ReservationClassRepository reservationClassRepository;
    private final TravellingModeRepository travellingModeRepository;
    @Autowired
    EsService esService;

    @Autowired
    UserRepository userRepository;

    @Override
    public ResponseDTO addbookingType(BookingTypeDTO bookingTypeDTO) {
        logger.info("MasterServiceImpl : addbookingType");
        validatBookingDTO(bookingTypeDTO);
        bookingTypeDTO.getNames().stream().forEach(type -> {
            BookingType bookingType = BookingType.builder().name(type).isActive(true).build();
            bookingTypeRepository.save(bookingType);

        });
        EsBookingTypeInfo esBookingTypeInfo = EsBookingTypeInfo.builder().build();
        BeanUtils.copyProperties(bookingTypeDTO,esBookingTypeInfo);
        esService.saveTestToElastic(esBookingTypeInfo);
        return new ResponseDTO(Constants.BOOKINGTYPE_ADDED_SUCCESSFULLY, StatusCodeEnum.OK.getStatusCode(), new ArrayList<>());
    }

    @Override
    public ResponseDTO editBookingType(BookingTypeDTO bookingTypeDTO, Long bookingTypeId) {
        logger.info("MasterServiceImpl : editBookingType");
        BookingType bookingType = bookingTypeRepository.findByBookingTypeId(bookingTypeId);
        if (Objects.isNull(bookingType)) {
            return new ResponseDTO(StatusCodeEnum.NOT_FOUND.getStatusCode(), Constants.BOOKING_TYPE_NOT_FOUND, null);
        } else {
            bookingTypeDTO.getNames().stream().forEach(type -> {
                bookingType.setName(type);
                bookingTypeRepository.save(bookingType);
            });
            return new ResponseDTO(StatusCodeEnum.OK.getStatusCode(), Constants.BOOKING_TYPE_UPDATED_SUCCESSFULLY, null);
        }
    }

    @Override
    public ResponseDTO deleteBookingType(Long bookingTypeId) {
        logger.info("MasterServiceImpl : deleteBookingType");
        BookingType bookingType = bookingTypeRepository.findByBookingTypeId(bookingTypeId);
        if (bookingType.getIsActive().equals(false)) {
            return new ResponseDTO(StatusCodeEnum.BAD_REQUEST.getStatusCode(), Constants.BOOKING_TYPE_ALREADY_DELETED, null);
        } else {
            if (Objects.nonNull(bookingType)) {
                bookingType.setIsActive(false);
                bookingTypeRepository.save(bookingType);
                return new ResponseDTO(StatusCodeEnum.OK.getStatusCode(), Constants.BOOKING_TYPE_DELETED_SUCCESSFULLY, null);
            } else {
                return new ResponseDTO(StatusCodeEnum.NOT_FOUND.getStatusCode(), Constants.BOOKING_TYPE_NOT_FOUND, null);
            }
        }
    }

    @Override
    public List<EsBookingTypeInfo> searchName(String name) throws JsonProcessingException {
        logger.info("MasterServiceImpl : searchName");
        return esService.searchByBookingTypeName(name);
    }

    @Override
    public ResponseDTO verify(String email, String password) {
        logger.info("MasterServiceImpl : verify");
        UserEntity user = userRepository.findByNameAndPassword(email, password);
        if (user.getIsVerified() == true) {
            return new ResponseDTO(StatusCodeEnum.BAD_REQUEST.getStatusCode(), Constants.ALREADY_VERIFIED, null);
        } else {
            user.setIsVerified(true);
        }
        userRepository.save(user);
        return new ResponseDTO(StatusCodeEnum.OK.getStatusCode(), Constants.VERIFIED_SUCCESSFULLY, null);
    }

    private void validatBookingDTO(BookingTypeDTO bookingTypeDTO) {
        for (BookingTypeEnums btEnum : BookingTypeEnums.values()) {
            if (!bookingTypeDTO.getNames().contains(btEnum.getBtValue())) {
                throw new InvalidBookingTypeException(Constants.Please_enter_BookingType_type);
            }
        }
        bookingTypeDTO.getNames().stream().forEach(bt -> {
            Optional<BookingType> bookingType = bookingTypeRepository.findByName(bt);
            if (bookingType.isPresent()) {
                throw new BookingTypeAlreadyExistException(Constants.BOOKING_TYPE_ALREADY_EXIST_EXCEPTION + bt);
            }
            if (bt.isEmpty()) {
                throw new BookingTypeNotEmptyException(Constants.BOOKING_TYPE_CANNOT_BE_EMPTY + bt);
            }
        });
    }

    @Override
    public ResponseDTO addReservationClass(ReservationClassDTO reservationClassDTO) {
        TravellingModeEntity travellingModeEntity = travellingModeRepository.findById(reservationClassDTO.getTmId()).orElseThrow(() -> new TravellingModeNotFound(Constants.TRAVELLING_MODE_NOT_FOUND_FOR_ID+ reservationClassDTO.getTmId()));
        BookingType bookingType = bookingTypeRepository.findById(reservationClassDTO.getBtypeId()).orElseThrow(() -> new BookingTypeNotFoundException(Constants.BOOKING_TYPE_NOT_FOUND + reservationClassDTO.getBtypeId()));
        if (bookingType.getId() == travellingModeEntity.getBookingTypeid()) {
            for (ReservationPriceDTO className : reservationClassDTO.getClassName()) {
                ReservationClassEntity reservationClassEntity = ReservationClassEntity.builder()
                        .className(className.getClassName()).rate(className.getRate()).tmId(travellingModeEntity.getId()).build();
                setBookinTypeFlag(bookingType, reservationClassEntity);
                reservationClassRepository.save(reservationClassEntity);
            }
            return new ResponseDTO(Constants.RESERVATION_CLASS_ADDED_SUCCESSFULLY, StatusCodeEnum.OK.getStatusCode(), new ArrayList<>());

        } else {
            throw new BookingTypeMisMatchException(Constants.BOOKINGTYPE_NOT_MATCH_WITH_TRAVELLING_MODE);
        }
    }

    private void setBookinTypeFlag(BookingType bookingType, ReservationClassEntity reservationClassEntity) {
        if (bookingType.getName().equalsIgnoreCase("plane")) {
            ReservationClassEntity.builder().isFlight(true).build();
        } else if (bookingType.getName().equalsIgnoreCase("train")) {
            ReservationClassEntity.builder().isTrain(true).build();
        } else {
            throw new ClassNotAvailableForBusException(Constants.CLASS_NOT_AVAILABLE_FOR_OTHERS);
        }
    }

    @Override
    public Map<String, List<ReservationClassResponseDTO>> get(String fors) {
        Map<String, Object> stringObjectMap = new HashMap<>();
        ReservationClassResponseDTO responseDTO = new ReservationClassResponseDTO();
        List<ReservationClassRsponseDTO> reservationClassRsponseDTOList = new ArrayList<>();
        List<ReservationClassEntity> flightClasses = reservationClassRepository.findPlaneClass();
        List<ReservationClassEntity> trainClasses = reservationClassRepository.findTrainClass();
        if (fors.equalsIgnoreCase(BookingTypeEnums.PLANE.getBtValue())) {
            flightClasses.stream().forEach(fc -> {
                ReservationClassRsponseDTO reservationClassDTO = ReservationClassRsponseDTO.builder()
                        .className(fc.getClassName())
                        .id(fc.getId())
                        .build();
                reservationClassRsponseDTOList.add(reservationClassDTO);
            });
        }
        if (fors.equalsIgnoreCase(BookingTypeEnums.TRAIN.getBtValue())) {
            trainClasses.stream().forEach(fc -> {
                ReservationClassRsponseDTO reservationClassDTO = ReservationClassRsponseDTO.builder()
                        .className(fc.getClassName())
                        .id(fc.getId())
                        .build();
                reservationClassRsponseDTOList.add(reservationClassDTO);
            });
        }
        return Map.of(fors, Collections.singletonList(responseDTO));
    }
}
