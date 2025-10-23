package org.example.notificationservice.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.notificationservice.model.MailSender;
import org.example.notificationservice.payload.MailPayload;
import org.example.notificationservice.payload.TransactionPayload;
import org.example.notificationservice.repository.MailRepository;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;

// Sends emails with PDF attachments and transaction notifications
@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class MailService {

    private final JavaMailSender javaMailSender;
    private final MailRepository mailRepository;

    // Send registration email with attached PDF
    @Transactional
    public void sendEmailPdf(MailPayload payload) {
        MailSender mail = new MailSender();
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true);
            message.setTo(payload.getTo());
            message.setSubject("X Bank");
            message.setText("Bank registration\n\nDear " + payload.getBody().toUpperCase() +
                    "\n\nFor security reasons, please do not share your account details with anyone.\n" +
                    "\nBank account details:\n"
                    + "\nSincerely, Bank X");

            File file = new File(payload.getFilePath());
            if (file.exists()) {
                message.addAttachment(file.getName(), file);
            } else {
                log.error("File not created");
            }

            mail.setEmail(payload.getTo());
            mail.setName(payload.getBody());
            mail.setMailSendStatus(true);
            mailRepository.save(mail);
            javaMailSender.send(mimeMessage);

        } catch (MessagingException e) {
            mail.setMailSendStatus(false);
            mailRepository.save(mail);
            throw new RuntimeException(e);
        }
    }

    // Send transaction details to user email
    @Transactional
    public void sendEmailLog(TransactionPayload payload) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true);

            message.setTo(payload.getTo());
            message.setSubject("X Bank");
            message.setText("For security reasons, please do not share your card information with anyone.\n\n"
                    + "ATM number: " + payload.getAtmId() + "\n"
                    + "Card number: " + payload.getCardNumber().toString().subSequence(0, 3) + "*******" + payload.getCardNumber().toString().substring(13) + "\n"
                    + "Operation name: " + payload.getType() + "\n"
                    + "Amount: " + payload.getAmount() + "\n"
                    + "Time: " + payload.getDateTime() + "\n"
            );

            javaMailSender.send(mimeMessage);

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
