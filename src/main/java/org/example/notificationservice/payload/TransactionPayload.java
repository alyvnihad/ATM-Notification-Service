package org.example.notificationservice.payload;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TransactionPayload {
    private String to;
    private Long atmId;
    private Long cardNumber;
    private String type;
    private Double amount;
    private LocalDateTime dateTime;

}
