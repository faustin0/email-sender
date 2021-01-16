package it.faustino.emailsender.models;

import org.immutables.builder.Builder;
import org.immutables.value.Value;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Immutable;
import org.springframework.data.relational.core.mapping.Embedded;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.lang.Nullable;

import java.time.LocalDateTime;
import java.util.OptionalLong;

@Table("emails")
@Value.Style(newBuilder = "builder")
@Immutable
public class EmailEntity {
    @Id
    @Nullable
    private final Long id;
    private final String body;
    private final LocalDateTime created;

    @Embedded.Empty
    private final MailHeader mailHeader;

    private EmailEntity(Long id, String body, LocalDateTime created, MailHeader mailHeader) {
        this.id = id;
        this.mailHeader = mailHeader;
        this.body = body;
        this.created = created;
    }

    public OptionalLong getId() {
        return this.id != null
                ? OptionalLong.of(this.id)
                : OptionalLong.empty();
    }

    public EmailAddress getFromEmailAddress() {
        return mailHeader.getFrom();
    }

    public EmailAddress getToEmailAddress() {
        return mailHeader.getTo();
    }

    public String getBody() {
        return body;
    }

    public String getSubject() {
        return mailHeader.getSubject();
    }

    public LocalDateTime getCreated() {
        return created;
    }


    public EmailEntity withId(Long id) {
        return new EmailEntity(id, body, created, mailHeader);
    }

    @Builder.Factory
    public static EmailEntity email(
            @Nullable Long id,
            String body,
            LocalDateTime created,
            MailHeader mailHeader
    ) {
        return new EmailEntity(id, body, created, mailHeader);
    }

    @Override
    public String toString() {
        return "EmailEntity{" +
                "id=" + id +
                ", body='" + body + '\'' +
                ", created=" + created +
                ", mailHeader=" + mailHeader +
                '}';
    }
}

