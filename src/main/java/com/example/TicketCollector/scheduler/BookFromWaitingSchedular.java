package com.example.TicketCollector.scheduler;

import com.example.TicketCollector.dto.BookTicketDTO;
import com.example.TicketCollector.entity.TravellingModeEntity;
import com.example.TicketCollector.entity.WaitingListEntity;
import com.example.TicketCollector.repository.TravellingModeRepository;
import com.example.TicketCollector.repository.WaitingListRepository;
import com.example.TicketCollector.service.EmailService;
import com.example.TicketCollector.service.UserService;
import com.itextpdf.text.DocumentException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@EnableScheduling
@RequiredArgsConstructor
@Slf4j
@Component
public class BookFromWaitingSchedular {

    private final WaitingListRepository waitingListRepository;
    private final TravellingModeRepository travellingModeRepository;
    private final EmailService emailService;
    private final UserService userService;

//    @Scheduled(cron = "* * * * * *")
    public void moveFromWaitingToBooking() throws Exception {
        log.info("BookFromWaitingScheduler :: started ::");
        waitingListRepository.findAll().stream().forEach(p -> System.out.println("-->" + p.getBookedOn()));
        List<WaitingListEntity> waitingListEntityList = waitingListRepository.findAll().stream().sorted(Comparator.comparing(WaitingListEntity::getBookedOn)).collect(Collectors.toList());
        log.info("BookFromWaitingScheduler :: started ::");
        if (!waitingListEntityList.isEmpty())
            for (WaitingListEntity wle : waitingListEntityList) {
                Optional<TravellingModeEntity> travellingModeEntity = travellingModeRepository.findById(wle.getTravellingModeId());
                if (travellingModeEntity.isPresent()) {
                    if (travellingModeEntity.get().getAvailableSeats() >= wle.getRequiredSeats()) {

                        BookTicketDTO bookTicketDTO = new BookTicketDTO();
                        BeanUtils.copyProperties(wle, bookTicketDTO);
                        userService.bookTickets(bookTicketDTO, travellingModeEntity.get().getId());
                        deleteFromWaitingList(wle.getId());
                    }
                }
            }
        log.info("BookFromWaitingScheduler :: terminate ::  ");

    }
    void deleteFromWaitingList(Long id) {
        waitingListRepository.deleteById(id) ;
    }
}
