package org.example.notificationservice.repository;

import org.example.notificationservice.model.MailSender;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MailRepository extends JpaRepository<MailSender, Long> {
}
