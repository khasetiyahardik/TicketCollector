package com.example.TicketCollector.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WaitingListEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String ticketClass;
    private Long requiredSeats;
    private String source;
    private String destination;
    private Long fair;
    private Date bookedOn;
    private Long travellingModeId;
    private Integer age;
    private String gender;
    private String userName;
    private Boolean isRegistered;
    private String vehicleNumber;
    private String email;
    private String contactNumber;
}
