package com.example.TicketCollector.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReservationClassDTO {
    private Long tmId;
    private List<ReservationPriceDTO> className;
    private Long btypeId;


}
