package com.example.TicketCollector.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TravellingSDEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String source;
    private String destination;
    @ManyToOne
    @JoinColumn(name = "sd_id")
    private SDEntity inBetweenDestinations;
    private Long tmId;
}
