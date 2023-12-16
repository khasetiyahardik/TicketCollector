package com.example.TicketCollector.serviceImpl;


import com.example.TicketCollector.constants.Constants;
import com.example.TicketCollector.dto.ResponseDTO;
import com.example.TicketCollector.dto.SDDTO;
import com.example.TicketCollector.dto.TravellingModeDTO;
import com.example.TicketCollector.dto.TravellingSDDTO;
import com.example.TicketCollector.entity.BookTicketEntity;
import com.example.TicketCollector.entity.SDEntity;
import com.example.TicketCollector.entity.TravellingModeEntity;
import com.example.TicketCollector.entity.TravellingSDEntity;
import com.example.TicketCollector.enums.StatusCodeEnum;
import com.example.TicketCollector.exception.TravellingModeNotFound;
import com.example.TicketCollector.exception.TravellingModeTimeExcetion;
import com.example.TicketCollector.repository.BookTicketRepository;
import com.example.TicketCollector.repository.SdRepository;
import com.example.TicketCollector.repository.TravellingModeRepository;
import com.example.TicketCollector.repository.TravellingSDRepository;
import com.example.TicketCollector.service.EsService;
import com.example.TicketCollector.service.TravellingModeService;
import com.example.TicketCollector.util.DateUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.rest.api.v2010.account.MessageCreator;
import com.twilio.type.PhoneNumber;
import lombok.RequiredArgsConstructor;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TravellingModeServiceImpl implements TravellingModeService {
    private final TravellingModeRepository travellingModeRepository;
    private final TravellingSDRepository travellingSDRepository;
    private final SdRepository sdRepository;
    private final EsService esService;
    private final BookTicketRepository bookTicketRepository;

    @Value("${twilio.account.sid}")
    private String ACCOUNT_SID;
    @Value("${twilio.auth.token}")
    private String AUTH_TOKEN;

    @Value("${twilio.from.mobile.number}")
    private String fromMobileNumber;

    @Override
    public ResponseDTO addTm(TravellingModeDTO travellingModeDTO) {
        Optional<TravellingModeEntity> travellingModeExisted = travellingModeRepository.findByTravellingModeNumber(travellingModeDTO.getTravelModeNumber());
        if (!travellingModeExisted.isPresent()) {
            TravellingModeEntity travellingModeEntity = TravellingModeEntity.builder()
                    .createdOn(new Date())
                    .reservationClass(travellingModeDTO.getReservationClass())
                    .isActive(true)
                    .isBus(travellingModeDTO.getIsBus())
                    .isTrain(travellingModeDTO.getIsTrain())
                    .isPlane(travellingModeDTO.getIsPlane())
                    .modifiedOn(null)
                    .availableSeats(travellingModeDTO.getTotalSeats())
                    .intervalTimeInHour(setInterval(travellingModeDTO.getArrivalTime(), travellingModeDTO.getDepartureTime()))
                    .build();
            BeanUtils.copyProperties(travellingModeDTO, travellingModeEntity);
            travellingModeEntity.setDepartureTime(travellingModeDTO.getDepartureTime());
            travellingModeEntity.setArrivalTime(travellingModeDTO.getArrivalTime());
            travellingModeRepository.save(travellingModeEntity);
            esService.saveTravellingModeToElastic(travellingModeEntity);
            return new ResponseDTO(Constants.TRAVELLINGMODE_ADDED_SUCCESSFULLY, StatusCodeEnum.OK.getStatusCode(), new ArrayList<>());

        } else {
            System.out.println("inside update -------------------------");
            BeanUtils.copyProperties(travellingModeDTO, travellingModeExisted.get());
            travellingModeExisted.get().setCreatedOn(new Date());
            travellingModeExisted.get().setModifiedOn(new Date());
            travellingModeExisted.get().setAvailableSeats(travellingModeDTO.getTotalSeats());
            travellingModeExisted.get().setIsActive(true);
            travellingModeExisted.get().setIntervalTimeInHour(setInterval(travellingModeDTO.getArrivalTime(), travellingModeDTO.getDepartureTime()));
            travellingModeRepository.save(travellingModeExisted.get());
            return new ResponseDTO(Constants.TRAVELLINGMODE_UPDATED_SUCCESSFULLY, StatusCodeEnum.OK.getStatusCode(), new ArrayList<>());

        }
    }

    private Long setInterval(Date arrivalTime, Date departureTime) {
        Long h1 = DateUtil.dateToHours(arrivalTime);
        Long h2 = DateUtil.dateToHours(departureTime);
        if (h1 - h2 < 0) {
            return -(h1 - h2);
        }
        return h1 - h2;
    }

    @Override
    public ResponseDTO delete(Long id) {
        Optional<TravellingModeEntity> travellingModeEntity = travellingModeRepository.findById(id)
                ;
        if (travellingModeEntity.isPresent()) {
            travellingModeEntity.get().setIsActive(false);
            travellingModeRepository.save(travellingModeEntity.get());
            List<BookTicketEntity> phoneNumberOfUsers = bookTicketRepository.findByTmId(id)
                    ;
            Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
            if (phoneNumberOfUsers.size() > 0) {
                phoneNumberOfUsers.stream().forEach(userNumber -> {
                    Message message = new MessageCreator(new PhoneNumber(userNumber.getContactNumber()),
                            new PhoneNumber(fromMobileNumber),
                            "Your journey from " + userNumber.getSource() + " to " + userNumber.getDestination() + " is cancelled ").create();
                    System.out.println(message.getSid());
                });
                return new ResponseDTO(StatusCodeEnum.OK.getStatusCode(), Constants.TRAVELLING_MODE_DELETED_SUCCESSFULLY, null);
            } else {
                return new ResponseDTO(StatusCodeEnum.NOT_FOUND.getStatusCode(), Constants.NO_USERS_BOOKED_TICKETS_WITH_TRAVELLING_MODE, null);
            }
        } else {
            throw new TravellingModeNotFound(Constants.TRAVELLING_MODE_NOT_FOUND + id);
        }
    }

    @Override
    @Transactional
    public ResponseDTO addSD(Long id, TravellingSDDTO travellingSDDTO) {
        TravellingModeEntity tm = travellingModeRepository.findById(id).orElseThrow(() -> new TravellingModeNotFound(Constants.TRAVELLING_MODE_NOT_FOUND));
        for (SDDTO sd : travellingSDDTO.getInBetweenDestinations()) {
            validateArrivalAndDepartureTime(tm.getArrivalTime(), tm.getDepartureTime(), sd.getArrivalTime(), sd.getDepartureTime());
            TravellingSDEntity travellingSDEntity = TravellingSDEntity.builder()
                    .id(id)

                    .source(travellingSDDTO.getSource())
                    .destination(travellingSDDTO.getDestination())

                    .build();
            BeanUtils.copyProperties(travellingSDDTO, travellingSDEntity);
            SDEntity sdEntity = SDEntity.builder()
                    .tmId(id)

                    .build();
            BeanUtils.copyProperties(sd, sdEntity);
            travellingSDEntity.setInBetweenDestinations(sdEntity);
            sdRepository.save(sdEntity);
            travellingSDRepository.save(travellingSDEntity);
        }
        return new ResponseDTO(Constants.TRAVELLING_SOURCE_AND_DESTINATION_ADDED_SUCCESSFULLY, StatusCodeEnum.OK.getStatusCode(), new ArrayList<>());
    }

    @Override
    public ResponseDTO editSD(Long tmId, TravellingSDDTO travellingSDDTO) {
        TravellingModeEntity tm = travellingModeRepository
                .findById(tmId)
                .orElseThrow(() -> new TravellingModeNotFound(Constants.TRAVELLING_MODE_NOT_FOUND));

        List<SDEntity> sdEntityList = sdRepository.findByTmId(tm.getId());
        List<TravellingSDEntity> travellingSDEntityList = travellingSDRepository.findByTmId(tmId);
        travellingSDEntityList.stream().forEach(trSD -> {
            trSD.setDestination(travellingSDDTO.getDestination());
            trSD.setSource(travellingSDDTO.getSource());
            trSD.setTmId(tmId);
        });

        sdEntityList.stream().forEach(sd ->
                travellingSDDTO.getInBetweenDestinations().stream().forEach(tsd -> {
                    Optional<SDEntity> sdEntity = sdRepository
                            .findByDestinationAndTravellingMode(tsd.getDestination(), sd.getTmId(), tsd.getPrice(), tsd.getArrivalTime(), tsd.getDepartureTime());
                    if (sdEntity.isEmpty()) {
                        BeanUtils.copyProperties(tsd, sd);
                        sdRepository.save(sd);
                    }
                })

        );

        return new ResponseDTO(Constants.TRAVELLING_SOURCE_AND_DESTINATION_EDITED_SUCCESSFULLY, StatusCodeEnum.OK.getStatusCode(), new ArrayList<>());
    }

    @Override
    public List<TravellingModeEntity> searchName(String name) throws JsonProcessingException {
        return esService.searchByTravellingModeName(name);
    }


    public void validateArrivalAndDepartureTime(Date arrivalTime, Date departureTime, Date des_arrivalTime, Date des_departureTime) {
        if (des_arrivalTime.after(arrivalTime) && des_arrivalTime.before(departureTime)) {
            throw new TravellingModeTimeExcetion("destination arrival time is in between arrival time and departure Time : " +
                    "" + "Arrival Time :" + arrivalTime +
                    " Departure Time :" + departureTime +
                    " And Destination Arrival Time :" + des_arrivalTime);
        }
        if (des_departureTime.after(arrivalTime) && des_departureTime.before(departureTime)) {
            throw new TravellingModeTimeExcetion("destination departure time is in between arrival time and departure Time : " +
                    "" + "Arrival Time :" + arrivalTime + " Departure Time :" + departureTime +
                    " And Destination Departure Time :" + des_departureTime);
        }
    }

    @Override
    public ResponseDTO importData(MultipartFile reapExcelDataFile) throws IOException, ParseException {
        List<TravellingModeEntity> travellingModeList = new ArrayList<>();
        XSSFWorkbook workbook = new XSSFWorkbook(reapExcelDataFile.getInputStream());
        XSSFSheet worksheet = workbook.getSheetAt(0);

        for (int i = 1; i < worksheet.getPhysicalNumberOfRows(); i++) {
            TravellingModeDTO travellingModeDTO = new TravellingModeDTO();
            TravellingModeEntity travelligModeEntity = new TravellingModeEntity();
            XSSFRow row = worksheet.getRow(i);
            if (row != null || reapExcelDataFile.isEmpty()) {
                travellingModeDTO.setBookingTypeid(Long.valueOf(row.getCell(0).getRawValue()));
                travellingModeDTO.setDepartureTime(row.getCell(1).getDateCellValue());
                travellingModeDTO.setArrivalTime(row.getCell(2).getDateCellValue());
                travellingModeDTO.setTravelModeNumber(row.getCell(3).getStringCellValue());
                travellingModeDTO.setTotalSeats(Long.valueOf(row.getCell(4).getRawValue()));
                travellingModeDTO.setIsPlane(row.getCell(5).getBooleanCellValue());
                travellingModeDTO.setIsTrain(row.getCell(6).getBooleanCellValue());
                travellingModeDTO.setIsBus(row.getCell(7).getBooleanCellValue());
                travellingModeDTO.setReservationClass(Long.valueOf(row.getCell(8).getRawValue()));
                BeanUtils.copyProperties(travellingModeDTO, travelligModeEntity);
                travelligModeEntity.setAvailableSeats(travellingModeDTO.getTotalSeats());
                travelligModeEntity.setCreatedOn(new Date());
                travelligModeEntity.setModifiedOn(new Date());
                travelligModeEntity.setIsActive(true);
                travelligModeEntity.setIntervalTimeInHour(Long.valueOf(row.getCell(9).getRawValue()));
                travellingModeList.add(travelligModeEntity);
            } else {
                return new ResponseDTO(StatusCodeEnum.BAD_REQUEST.getStatusCode(), Constants.SOME_FIELD_IS_MISSING_IN_EXCEL, null);
            }
        }
        travellingModeRepository.saveAll(travellingModeList);
        return new ResponseDTO(StatusCodeEnum.OK.getStatusCode(), "Data imported successfully", new ArrayList<>());
    }
}