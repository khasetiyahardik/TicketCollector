package com.example.TicketCollector.dto.elastic;

import com.example.TicketCollector.dto.EsBookingTypeInfo;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class HitsArrayDTO {

    private String _index;
    private String _type;
    private String _id;
    private Double _score;
    private EsBookingTypeInfo _source;

}
