package org.example.notificationservice.payload;

import lombok.Data;

@Data
public class MailPayload {
    private String to;
    private String body;
    private String filePath;
}
