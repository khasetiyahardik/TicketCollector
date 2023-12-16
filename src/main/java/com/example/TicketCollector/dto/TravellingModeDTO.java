package com.example.TicketCollector.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.ManyToOne;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TravellingModeDTO {
    private Long bookingTypeid;

    @JsonFormat(pattern="YYYY-MM-DD HH:MM")
    private Date departureTime;

    @JsonFormat(pattern="YYYY-MM-DD HH:MM")
    private Date arrivalTime;
    private String travelModeNumber;
    private List<String> route;
    private Long totalSeats;
    private Boolean isPlane;
    private Boolean isTrain;
    private Boolean isBus;
    private Long reservationClass;
}
