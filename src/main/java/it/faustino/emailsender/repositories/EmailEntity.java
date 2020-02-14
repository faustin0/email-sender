package it.faustino.emailsender.repositories;

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
}
