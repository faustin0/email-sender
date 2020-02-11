package it.faustino.emailsender.services.impl;

import it.faustino.emailsender.models.Email;
import it.faustino.emailsender.services.EmailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
public class EmailSenderImpl implements EmailSender {

    private final JavaMailSender mailSender;

    public EmailSenderImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public CompletableFuture<Void> sendSimpleMail(Email email) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email.getTo());
        message.setSubject(email.getSubject());
        message.setText(email.getSubject());
        message.setFrom(email.getFrom());

        return CompletableFuture.runAsync(
                () -> mailSender.send(new SimpleMailMessage())
        );
    }
}
