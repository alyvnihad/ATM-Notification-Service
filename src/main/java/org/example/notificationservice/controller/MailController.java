package org.example.notificationservice.controller;

import lombok.RequiredArgsConstructor;
import org.example.notificationservice.payload.MailPayload;
import org.example.notificationservice.service.MailService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/notifications")
@RequiredArgsConstructor
public class MailController {

    private final MailService mailService;

    @PostMapping("/register-send")
    public void sendEmailPdf(@RequestBody MailPayload payload){
        mailService.sendEmailPdf(payload);
    }
}
