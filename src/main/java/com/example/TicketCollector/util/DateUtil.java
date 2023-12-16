package com.example.TicketCollector.util;

import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Slf4j
public class DateUtil {

    public static Long dateToHours(Date date) {
        return Long.valueOf(date.getHours() + (date.getMinutes() / 60) + (date.getSeconds() / 3600));
    }

    public static Long getDay(Date date) {
        log.info("day is :" + date.getDay());
        return Long.valueOf(date.getDay());

    }

    public static Long addMinIntoCurrentDate(Integer min) {
        Instant currentTime = Instant.now();
        Instant newTime = currentTime.plusSeconds(min);
        return newTime.getEpochSecond();
    }
}
