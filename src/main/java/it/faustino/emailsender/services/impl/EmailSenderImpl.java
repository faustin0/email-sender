package it.faustino.emailsender.services.impl;

import it.faustino.emailsender.models.Email;
import it.faustino.emailsender.services.EmailSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
public class EmailSenderImpl implements EmailSender {

    private final static Logger log = LoggerFactory.getLogger(EmailSenderImpl.class);

    private final JavaMailSender mailSender;

    public EmailSenderImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public CompletableFuture<Void> sendSimpleMail(Email email) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email.getTo());
        message.setSubject(email.getSubject());
        message.setText(email.getBody());
        message.setFrom(email.getFrom());

        log.debug("sending mail {}", email);
        return CompletableFuture.runAsync(
                () -> mailSender.send(message)
        );
    }
}
