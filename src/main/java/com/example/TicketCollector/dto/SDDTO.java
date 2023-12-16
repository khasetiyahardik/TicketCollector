package com.example.TicketCollector.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SDDTO {
    private String destination;
    private Double Price;
    @JsonFormat(pattern="dd-MM-yyyy hh:mm:ss")
    private Date arrivalTime;
    @JsonFormat(pattern="dd-MM-yyyy hh:mm:ss")
    private Date departureTime;
}
