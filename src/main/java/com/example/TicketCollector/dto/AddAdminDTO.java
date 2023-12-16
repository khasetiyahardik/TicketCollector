package com.example.TicketCollector.dto;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class AddAdminDTO {
    private String role;

    @NotEmpty(message = "Name should not be empty")
    private String name;

    @NotEmpty(message = "Contact number should not be empty")
    @Size(min = 10, max = 10, message = "Phone number should be 10 digits")
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
