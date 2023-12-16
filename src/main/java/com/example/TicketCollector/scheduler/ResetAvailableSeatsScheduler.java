package com.example.TicketCollector.scheduler;

import com.example.TicketCollector.entity.TravellingModeEntity;
import com.example.TicketCollector.repository.TravellingModeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
@EnableScheduling
@Slf4j
public class ResetAvailableSeatsScheduler {

    @Autowired
    TravellingModeRepository travellingModeRepository;

    //@Scheduled(cron = "* * * * * *")
    void resetAvailableSeats() {
        log.info("reset available seats scheduler started:");
        List<TravellingModeEntity> travellingModeEntityList = travellingModeRepository.findAll();
        travellingModeEntityList.stream().filter(seats -> seats.getDepartureTime().compareTo(new Date()) < 0).forEach(seats -> seats.setAvailableSeats(seats.getTotalSeats()));
        travellingModeRepository.saveAll(travellingModeEntityList);
        log.info("reset available seats scheduler ended:");
    }
}
