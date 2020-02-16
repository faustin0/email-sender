package it.faustino.emailsender.models;

import org.immutables.builder.Builder;
import org.immutables.value.Value;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Immutable;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.lang.Nullable;

import java.time.LocalDateTime;

@Table("emails")
@Immutable
@Value.Style(newBuilder = "builder")
public class EmailEntity {

    @Id
    @Nullable
    private Long id;
    private String sender;
    private String to;
    private String body;
    private String subject;
    private LocalDateTime created;

    private EmailEntity(Long id, String sender, String to, String body, String subject, LocalDateTime created) {
        this.id = id;
        this.sender = sender;
        this.to = to;
        this.body = body;
        this.subject = subject;
        this.created = created;
    }

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


    @Builder.Factory
    public static EmailEntity email(
            @Nullable Long id,
            String sender,
            String to,
            String body,
            String subject,
            LocalDateTime created
    ) {
        return new EmailEntity(id, sender, to, body, subject, created);
    }

    @Override
    public String toString() {
        return "EmailEntity{" +
                "id=" + id +
                ", sender='" + sender + '\'' +
                ", to='" + to + '\'' +
                ", body='" + body + '\'' +
                ", subject='" + subject + '\'' +
                ", created=" + created +
                '}';
    }
}
