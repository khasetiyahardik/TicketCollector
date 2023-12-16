package com.example.TicketCollector.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SearchResponseDTO {
    private String travellingModeTitle;
    private Date arrivalTime;
    private Date departureTime;
    private String source;
    private String destination;
    private Number tmId;
}
