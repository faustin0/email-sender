package it.faustino.emailsender.repositories;

import it.faustino.emailsender.models.Email;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Table("emails")
public class EmailEntity {
    @Id
    Long id;
    String sender;
    String to;
    String body;
    String subject;
    LocalDateTime created;

    public Long getId() {
        return id;
    }

    public String getSender() {
        return sender;
    }

    public String getTo() {
        return to;
    }

    public String getBody() {
        return body;
    }

    public String getSubject() {
        return subject;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public static EmailEntity fromEmail(Email email) {
        EmailEntity emailEntity = new EmailEntity();
        emailEntity.sender = email.getFrom();
        emailEntity.to = email.getTo();
        emailEntity.body = email.getBody();
        emailEntity.subject = email.getSubject();
        emailEntity.created = LocalDateTime.now();
        return emailEntity;
    }
}
