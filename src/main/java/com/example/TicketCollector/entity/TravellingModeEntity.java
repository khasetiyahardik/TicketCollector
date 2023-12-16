package com.example.TicketCollector.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TravellingModeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long bookingTypeid;
    private Date departureTime;
    private Date arrivalTime;
    private String travelModeNumber;
    private Date createdOn;
    private Date modifiedOn;
    @ElementCollection
    private List<String> route;
    private Long availableSeats;
    private Long totalSeats;
    private Boolean isPlane;
    private Boolean isTrain;
    private Boolean isBus;
    private Long reservationClass;
    private Boolean isActive;
    private Long intervalTimeInHour;

}
