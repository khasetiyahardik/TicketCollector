package com.example.TicketCollector.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReservationClassEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private Boolean isTrain;
    private Boolean isFlight;
    private String className;
    private Long tmId;
    private Double rate;
}
