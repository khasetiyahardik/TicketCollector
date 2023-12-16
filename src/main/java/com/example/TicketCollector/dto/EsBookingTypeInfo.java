package com.example.TicketCollector.dto;

import lombok.*;

import java.util.Set;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EsBookingTypeInfo {

    private Set<String> names;
}
