package com.example.TicketCollector.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class BookTicketEntity {
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
    private Boolean isCanceled;
    private Boolean isBooked;
    private String contactNumber;

}
