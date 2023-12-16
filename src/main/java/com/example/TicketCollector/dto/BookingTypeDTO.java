package com.example.TicketCollector.dto;

import lombok.*;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookingTypeDTO {
    private Set<String> names;

}
