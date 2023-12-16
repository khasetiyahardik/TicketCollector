package com.example.TicketCollector.dto.elastic;

import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class HitsDTO {

    private Long max_score;
    private TotalDTO total;
    private List<HitsArrayDTO> hits;
}