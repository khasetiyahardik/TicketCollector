package com.example.TicketCollector.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErrorResponse {

    private String errorCode;
    private String errorMsg;
    private Object data;

}
