package com.example.TicketCollector.dto;

import com.example.TicketCollector.entity.SDEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TravellingSDDTO {
    private String source;
    private String destination;
    private Double totalTravellingPrice;
    private List<SDDTO> inBetweenDestinations;
}
