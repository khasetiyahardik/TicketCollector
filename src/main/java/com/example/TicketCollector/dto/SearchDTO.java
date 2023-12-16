package com.example.TicketCollector.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SearchDTO {
    @NotEmpty(message = "Name should not be empty")
    private String name;
    @NotEmpty(message = "Name should not be empty")
    private String source;
    @NotEmpty(message = "Name should not be empty")
    private String destination;

    @JsonFormat(pattern = "dd-MM-yyyy")
    private Date date;
}
