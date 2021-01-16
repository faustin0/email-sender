package it.faustino.emailsender.services.impl;

import it.faustino.emailsender.models.EmailEntity;
import it.faustino.emailsender.services.EmailSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;

@Service
public class EmailSenderImpl implements EmailSender {

    private static final Logger log = LoggerFactory.getLogger(EmailSenderImpl.class);

    private final JavaMailSender mailSender;

    public EmailSenderImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public CompletableFuture<Void> sendSimpleMail(EmailEntity email) {
        Objects.requireNonNull(email, "email cant be null");

        var message = new SimpleMailMessage();
        message.setTo(email.getToEmailAddress().getAddress());
        message.setSubject(email.getSubject());
        message.setText(email.getBody());
        message.setFrom(email.getFromEmailAddress().getAddress());

        log.debug("sending mail {}", email);
        return CompletableFuture.runAsync(
                () -> mailSender.send(message)
        );
    }
}
