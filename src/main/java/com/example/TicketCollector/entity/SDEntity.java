package com.example.TicketCollector.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SDEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long sd_id;
    private String source;
    private String destination;
    private Double Price;
    private Date arrivalTime;
    private Date departureTime;
    private Long tmId;
}
