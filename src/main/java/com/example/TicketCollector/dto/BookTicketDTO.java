package com.example.TicketCollector.dto;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BookTicketDTO {
    @NotEmpty(message = "Ticket class should not be empty")
    private String ticketClass;

    @NotEmpty(message = "Name should not be empty")
    private String userName;

    @NotNull(message = "Age should not be null")
    private Integer age;

    @NotEmpty(message = "Gender should not be empty")
    private String gender;

    @NotNull(message = "Required seats should not be null")
    private Long requiredSeats;

    @NotEmpty(message = "Source should not be empty")
    private String source;

    @NotEmpty(message = "Destination should not be empty")
    private String destination;

    @Pattern(message = "Write email in proper format", regexp = "^[a-zA-Z0-9_+&*-]+(?:\\." +
            "[a-zA-Z0-9_+&*-]+)*@" +
            "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
            "A-Z]{2,7}$")
    @NotEmpty(message = "Email should not be empty")
    private String email;

    @NotEmpty(message = "Contact number should not be empty")
    private String contactNumber;
}
