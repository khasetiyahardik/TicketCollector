package com.example.TicketCollector.dto.elastic;


import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ShardsDTO {
    private Long total;
    private Long successful;
    private Long skipped;
    private Long failed;

}
