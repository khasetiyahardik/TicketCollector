package com.example.TicketCollector.dto;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class RegisterUserDTO {

    private String role;

    @NotEmpty(message = "Name should not be empty")
    private String name;

    @NotEmpty(message = "ContactNumber should not be empty")
    private String contactNumber;

    @Pattern(message = "Write email in proper format", regexp = "^[a-zA-Z0-9_+&*-]+(?:\\." +
            "[a-zA-Z0-9_+&*-]+)*@" +
            "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
            "A-Z]{2,7}$")
    @NotEmpty(message = "Email should not be empty")
    private String email;

    @NotEmpty(message = "Password should not be empty")
    private String password;
}
