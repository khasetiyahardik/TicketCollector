package com.example.TicketCollector.serviceImpl;


import com.example.TicketCollector.constants.Constants;
import com.example.TicketCollector.dto.*;
import com.example.TicketCollector.entity.*;
import com.example.TicketCollector.enums.StatusCodeEnum;
import com.example.TicketCollector.exception.*;
import com.example.TicketCollector.helper.PdfGeneratorForTicket;
import com.example.TicketCollector.repository.*;
import com.example.TicketCollector.service.CloudinaryService;
import com.example.TicketCollector.service.EmailService;
import com.example.TicketCollector.service.RazorpayService;
import com.example.TicketCollector.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeWriter;
import com.razorpay.RazorpayException;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.rest.api.v2010.account.MessageCreator;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import javax.mail.MessagingException;
import javax.transaction.Transactional;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    @Value("${twilio.account.sid}")
    private String ACCOUNT_SID;
    @Value("${twilio.auth.token}")
    private String AUTH_TOKEN;

    @Value("${twilio.from.mobile.number}")
    private String fromMobileNumber;


    @Autowired
    UserRepository userRepository;

    @Autowired
    BookTicketRepository bookTicketRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    BookingTypeRepository bookingTypeRepository;

    @Autowired
    TravellingModeRepository travellingModeRepository;

    @Autowired
    SdRepository sdRepository;

    @Autowired
    TravellingSDRepository travellingSDRepository;

    @Autowired
    ReservationClassRepository classRepository;
    @Autowired
    EmailService emailService;

    @Autowired
    SearchRepository searchRepository;

    @Autowired
    WaitingListRepository waitingListRepository;

    @Autowired
    CloudinaryService cloudinaryService;

    @Autowired
    PdfGeneratorForTicket pdfGeneratorForTicket;

    @Autowired
    RazorpayService razorpayService;


    @Override
    public ResponseDTO registerUser(RegisterUserDTO registerUserDTO) throws MessagingException {
        if (registerUserDTO.getRole().equalsIgnoreCase("User")) {
            validateUserEmailNumberAndName(registerUserDTO);
            return new ResponseDTO(StatusCodeEnum.OK.getStatusCode(), Constants.USER_ADDED_SUCCESSFULLY, null);
        } else {
            throw new InvalidUserRoleException(Constants.INVALID_USER_ROLE);
        }
    }

    @Override
    public ResponseDTO getBookingType() {
        List<BookingType> bookingTypeList = bookingTypeRepository.findAll();
        if (bookingTypeList.isEmpty()) {
            return new ResponseDTO(StatusCodeEnum.NOT_FOUND.getStatusCode(), Constants.BOOKING_TYPE_NOT_FOUND, null);
        } else {
            return new ResponseDTO(StatusCodeEnum.OK.getStatusCode(), Constants.BOOKING_TYPE_FETCHED_SUCCESSFULLY, bookingTypeList);
        }
    }

    @Override
    public ResponseDTO getTravellingMode(Long bookingTypeId) {
        List<TravellingModeEntity> entityList = travellingModeRepository.findByBookingTypeId(bookingTypeId);
        if (entityList.size() != 0) {
            return new ResponseDTO(StatusCodeEnum.OK.getStatusCode(), Constants.TRAVELLING_MODE_FETCHED_SUCCESSFULLY, entityList);
        } else {
            return new ResponseDTO(StatusCodeEnum.NOT_FOUND.getStatusCode(), Constants.TRAVELLING_MODE_NOT_FOUND_FOR_ID, null);
        }
    }

    @Override
    @Transactional
    public ResponseDTO bookTickets(BookTicketDTO bookTicketDTO, Long travellingModeId) throws Exception {


        TravellingModeEntity travellingModeEntity = travellingModeRepository.findByTravellingModeId(travellingModeId);
        validateClass(bookTicketDTO.getTicketClass(), travellingModeEntity.getId());
        BookTicketEntity bookTicketEntity;
        if (Objects.nonNull(travellingModeEntity)) {
            bookTicketEntity = BookTicketEntity.builder()
                    .travellingModeId(travellingModeId)
                    .vehicleNumber(travellingModeEntity.getTravelModeNumber())
                    .bookedOn(new Date())
                    .userName(bookTicketDTO.getUserName())
                    .ticketClass(bookTicketDTO.getTicketClass())
                    .email(bookTicketDTO.getEmail())
                    .contactNumber(bookTicketDTO.getContactNumber())
                    .isCanceled(false)
                    .age(bookTicketDTO.getAge())
                    .gender(bookTicketDTO.getGender())
                    .build();
            Long reqSeats = bookTicketDTO.getRequiredSeats();
            Long availableSeats = travellingModeEntity.getAvailableSeats();
            List<TravellingSDEntity> travellingSDEntityList = travellingSDRepository.findByTmId(travellingModeEntity.getId());
            if (!travellingSDEntityList.stream().filter(t -> t.getSource().equalsIgnoreCase(bookTicketDTO.getSource())).toList().isEmpty()) {
                bookTicketEntity.setSource(bookTicketDTO.getSource());
            } else {
                return new ResponseDTO(StatusCodeEnum.NOT_FOUND.getStatusCode(), Constants.SOURCE_NOT_AVAILABLE_IN_ROUT, bookTicketDTO.getSource());
            }
            if (!travellingSDEntityList.stream().filter(t -> t.getDestination().equalsIgnoreCase(bookTicketDTO.getDestination())).toList().isEmpty()) {
                bookTicketEntity.setDestination(bookTicketDTO.getDestination());
            } else {
                return new ResponseDTO(StatusCodeEnum.NOT_FOUND.getStatusCode(), Constants.DESTINATION_NOT_AVAILABLE_IN_ROUT, bookTicketDTO.getDestination());
            }
            if (reqSeats > availableSeats && availableSeats == 0) {
                if (travellingModeEntity.getBookingTypeid() == 3) {
                    addIntoWaitingList(bookTicketDTO, travellingModeEntity);
                } else {
                    return new ResponseDTO(StatusCodeEnum.BAD_REQUEST.getStatusCode(), Constants.NO_SEATS_AVAILABLE, new ArrayList<>());
                }
            } else {
                bookTicketEntity.setRequiredSeats(reqSeats);
            }
            Double fair = findFair(travellingModeEntity, bookTicketDTO);
            bookTicketEntity.setFair(fair.longValue() * bookTicketDTO.getRequiredSeats());
            UserEntity userEntity = userRepository.findByVerifiedEmail(bookTicketEntity.getEmail());
            if (Objects.nonNull(userEntity)) {
                bookTicketEntity.setIsRegistered(true);
            } else {
                throw new LoginForGuestException(Constants.PLEASE_LOGIN_FOR_BOOK_TICKET);
            }
            if (reqSeats > availableSeats)
                return new ResponseDTO(StatusCodeEnum.BAD_REQUEST.getStatusCode(), Constants.NO_SEATS_AVAILABLE_SO_YOUR_REQUEST_HAS_BEEN_INTO_WAITING_LIST, new ArrayList<>());
            else {
                updateAvailableSeats(travellingModeEntity, bookTicketEntity.getRequiredSeats());

                String pdfPath = pdfGeneratorForTicket.generate(bookTicketEntity, travellingModeEntity);
                String QrCodeImagePath = uploadQr(pdfPath);

                Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
                Message message = new MessageCreator(new PhoneNumber(bookTicketEntity.getContactNumber()),
                        new PhoneNumber(fromMobileNumber),
                        "Your ticket is confirmed for " + bookTicketEntity.getSource() + " to " + bookTicketEntity.getDestination() + ".").create();
                bookTicketRepository.save(bookTicketEntity);
                emailService.sendTicket(bookTicketEntity, travellingModeEntity, pdfPath, QrCodeImagePath);
                return new ResponseDTO(StatusCodeEnum.OK.getStatusCode(), Constants.TICKET_BOOKED_SUCCESSFULLY, fair * bookTicketDTO.getRequiredSeats());
            }
        } else {
            return new ResponseDTO(StatusCodeEnum.NOT_FOUND.getStatusCode(), Constants.TRAVELLING_MODE_NOT_FOUND_FOR_ID, null);
        }
    }

    @Override
    public ResponseDTO search(SearchDTO searchDTO) {
        return new ResponseDTO(StatusCodeEnum.OK.getStatusCode(), Constants.DATA_FETCHED_SUCCESSFULLY, searchRepository.getSearchList(searchDTO));
    }

    private void addIntoWaitingList(BookTicketDTO bookTicketDTO, TravellingModeEntity travellingModeEntity) {
        WaitingListEntity waitingListEntity = WaitingListEntity.builder()
                .travellingModeId(travellingModeEntity.getId())
                .build();
        BeanUtils.copyProperties(bookTicketDTO, waitingListEntity);
        waitingListRepository.save(waitingListEntity);
    }

    @Override
    public ResponseDTO cancelBooking(Long id, Long travellingModeId, Long seats) {
        Optional<BookTicketEntity> bookTicketEntity = bookTicketRepository.findById(id);
        Optional<TravellingModeEntity> travellingMode = travellingModeRepository.findById(travellingModeId);
        TravellingModeEntity travellingModeEntity = travellingModeRepository.findByIsActive(travellingModeId);
        if (travellingModeEntity != null) {
            throw new TravellingModeNotFound(Constants.TRAVELLING_MODE_NOT_FOUND_FOR_ID);
        }
        if (travellingMode.isEmpty()) {
            throw new TravellingModeNotFound(Constants.TRAVELLING_MODE_NOT_FOUND_FOR_ID);
        } else {
            if (bookTicketEntity.isPresent()) {
                if (bookTicketEntity.get().getIsCanceled().equals(true)) {
                    throw new TicketAlreadyCancelledException(Constants.TICKET_ALREADY_CANCELLED);
                } else {
                    bookTicketEntity.get().setIsCanceled(true);

                    if (seats > bookTicketEntity.get().getRequiredSeats()) {
                        throw new CancelSeatsAreGreaterThanBookedTicketsException("Your booked tickets are " + bookTicketEntity.get().getRequiredSeats());
                    } else {
                        Long wantToCancel = seats;
                        Long availableSeatsToCancel = bookTicketEntity.get().getRequiredSeats();
                        Long seatsAfterCancel = 0l;
                        seatsAfterCancel = (availableSeatsToCancel != 0 ? availableSeatsToCancel : wantToCancel) - wantToCancel;
                        if (seatsAfterCancel == 0) {
                            Long addToAvailableSeats = bookTicketEntity.get().getRequiredSeats();
                            Long finalAvailableSeats = travellingMode.get().getAvailableSeats() + addToAvailableSeats;
                            travellingMode.get().setAvailableSeats(finalAvailableSeats);
                        } else {
                            Long addToAvailableSeats = seatsAfterCancel;
                            Long finalAvailableSeats = travellingMode.get().getAvailableSeats() + addToAvailableSeats;
                            travellingMode.get().setAvailableSeats(finalAvailableSeats);
                        }
                    }
                }
                bookTicketRepository.save(bookTicketEntity.get());
                return new ResponseDTO(StatusCodeEnum.OK.getStatusCode(), Constants.TICKET_CANCELLED_SUCCESSFULLY, null);
            } else {
                throw new InvalidBookingIdException(Constants.NO_BOOKING_AVAILABLE_ON_THIS_ID);
            }
        }
    }

    private void updateAvailableSeats(TravellingModeEntity travellingModeEntity, Long requiredSeats) {
        Long totalSeats = travellingModeEntity.getTotalSeats();
        travellingModeEntity.setAvailableSeats(totalSeats - requiredSeats);
        travellingModeRepository.save(travellingModeEntity);
    }

    private Double findFair(TravellingModeEntity travellingModeEntity, BookTicketDTO bookTicketDTO) {
        Double fair = 0.0;
        List<ReservationClassEntity> reservationClassEntityList = classRepository.findByTmID(travellingModeEntity.getId());
        Double classWiseRate = getRate(reservationClassEntityList, bookTicketDTO.getTicketClass());
        List<TravellingSDEntity> travellingSDEntityList = travellingSDRepository.findByTmId(travellingModeEntity.getId());
        if (travellingSDEntityList.get(0).getSource().equalsIgnoreCase(bookTicketDTO.getSource()) && travellingSDEntityList.get(0).getDestination().equalsIgnoreCase(bookTicketDTO.getDestination())) {
            List<Long> sdIds = getSDIds(travellingSDEntityList);
            if (classWiseRate != 0)
                fair += sdRepository.findAllById(sdIds).stream().mapToDouble(SDEntity::getPrice).sum() * classWiseRate;
            else fair += sdRepository.findAllById(sdIds).stream().mapToDouble(SDEntity::getPrice).sum();
            return fair;
        }
        for (TravellingSDEntity tsd : travellingSDEntityList) {
            List<Long> sdIds = getSDIds(travellingSDEntityList);
            if (tsd.getInBetweenDestinations().getDestination().equalsIgnoreCase(bookTicketDTO.getDestination()) && tsd.getInBetweenDestinations().getDestination().equalsIgnoreCase(bookTicketDTO.getSource())) {
                if (classWiseRate != 0)
                    fair += getFairFromSD(sdIds, bookTicketDTO.getSource(), bookTicketDTO.getDestination()) * classWiseRate;
                else fair += getFairFromSD(sdIds, bookTicketDTO.getSource(), bookTicketDTO.getDestination());
            } else if (tsd.getSource().equalsIgnoreCase(bookTicketDTO.getDestination()) == Boolean.FALSE) {
                if (classWiseRate != 0)
                    fair += getFairFromDestination(sdIds, bookTicketDTO.getDestination()) * classWiseRate;
                else fair += getFairFromDestination(sdIds, bookTicketDTO.getDestination());
                break;
            } else {
                Double TotalFair = sdRepository.findAllById(sdIds).stream().mapToDouble(SDEntity::getPrice).sum();
                if (classWiseRate != 0)
                    fair = TotalFair - getFairFromDestination(sdIds, bookTicketDTO.getDestination()) * classWiseRate;
                else fair = TotalFair - getFairFromDestination(sdIds, bookTicketDTO.getDestination());
            }
        }

        return fair;
    }

    private void validateClass(String ticketClass, Long tmId) {
        Optional<ReservationClassEntity> re = classRepository.finByClassName(ticketClass, tmId);
        if (re.isEmpty()) {
            throw new ReservationClassNotFoundException(Constants.RESERVATION_CLASS_NOT_FOUND_FOR_TRAVELLING_MODE_ID + tmId);
        }
    }

    private Double getFairFromDestination(List<Long> sdIds, String destination) {
        Double f = sdRepository.findAllById(sdIds).stream().filter(s -> s.getDestination().equalsIgnoreCase(destination)).mapToDouble(SDEntity::getPrice).sum();
        return f;
    }

    private Double getFairFromSD(List<Long> sdIds, String source, String destination) {
        Double f1 = sdRepository.findAllById(sdIds).stream().filter(s -> s.getDestination().equalsIgnoreCase(source)).mapToDouble(SDEntity::getPrice).sum();
        Double f2 = sdRepository.findAllById(sdIds).stream().filter(s -> s.getDestination().equalsIgnoreCase(destination)).mapToDouble(SDEntity::getPrice).sum();
        return f1 + f2;
    }

    private List<Long> getSDIds(List<TravellingSDEntity> travellingSDEntityList) {
        return travellingSDEntityList.stream().map(t -> t.getInBetweenDestinations().getSd_id()).toList();
    }

    private Double getRate(List<ReservationClassEntity> reservationClassEntityList, String className) {
        return reservationClassEntityList.stream().filter(r -> r.getClassName().equalsIgnoreCase(className)).mapToDouble(ReservationClassEntity::getRate).sum();
    }


    private void validateUserEmailNumberAndName(RegisterUserDTO registerUserDTO) throws MessagingException {
        Long existingData = userRepository.findByNoAndEmail(registerUserDTO.getContactNumber(), registerUserDTO.getEmail(), registerUserDTO.getName());
        if (existingData == 0) {
            Set<String> roleEntities = new HashSet<>();
            roleEntities.add(registerUserDTO.getRole());
            UserEntity userEntity = UserEntity.builder()
                    .roles(roleEntities)
                    .createdOn(new Date())
                    .isVerified(false)
                    .isActive(true)
                    .updatedOn(null)
                    .build();
            BeanUtils.copyProperties(registerUserDTO, userEntity);
            userEntity.setPassword(passwordEncoder.encode(registerUserDTO.getPassword()));
            userRepository.save(userEntity);
            emailService.sendVerifyEmail(userEntity);
        } else {
            throw new EmailOrNoAlreadyExistException(Constants.ALREADY_EXIST);
        }
    }

    public String uploadQr(String path) throws Exception {
        String qrCodeContent = path;

        Map<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");

        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(qrCodeContent, BarcodeFormat.QR_CODE, 250, 250, hints);

        BufferedImage qrCodeImage = MatrixToImageWriter.toBufferedImage(bitMatrix);
        return cloudinaryService.uploadImageToCloudinary(qrCodeImage);
    }

    public static String decodeQRCode(String QrPath) throws IOException {
        BufferedImage image = ImageIO.read(new File(QrPath));
        if (image == null) {
            throw new IOException("Failed to read the QR code image");
        }
        Map<DecodeHintType, Object> hints = new EnumMap<>(DecodeHintType.class);
        hints.put(DecodeHintType.TRY_HARDER, Boolean.TRUE);

        BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource(image)));
        try {
            Result result = new MultiFormatReader().decode(binaryBitmap, hints);
            return result.getText();
        } catch (Exception e) {
            throw new IOException("Failed to decode the QR code", e);
        }
    }

    @Override
    public ResponseDTO initiatePayment(PaymentDTO dto) throws RazorpayException, JsonProcessingException {
        return razorpayService.initiatePayment(dto);
    }

    @Override
    public ResponseDTO initiateQRPayment(PaymentDTO dto) {
        return razorpayService.initiateQRPayment(dto);
    }
}