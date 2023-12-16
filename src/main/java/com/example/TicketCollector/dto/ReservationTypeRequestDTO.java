package com.example.TicketCollector.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReservationTypeRequestDTO {

    private Boolean isPlane;
    private Boolean isTrain;
}
