package com.example.TicketCollector.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.annotation.Nonnull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDTO {

    private Integer amount;
    private String currency;
    private String paymentMethod;
    private String userName;
    private String email;
    private String contact;
}
