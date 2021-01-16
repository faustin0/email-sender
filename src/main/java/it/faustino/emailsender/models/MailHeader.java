package it.faustino.emailsender.models;

import org.springframework.data.annotation.Immutable;
import org.springframework.data.relational.core.mapping.Embedded;

@Immutable
public class MailHeader {

    @Embedded.Empty(prefix = "from_")
    private final EmailAddress from;

    @Embedded.Empty(prefix = "to_")
    private final EmailAddress to;

    private final String subject;

    private MailHeader(EmailAddress from, EmailAddress to, String subject) {
        this.from = from;
        this.to = to;
        this.subject = subject;
    }

    public static MailHeader createMailHeader(String sender, String to, String subject) {
        return new MailHeader(
                EmailAddress.createValidEmailAddress(sender),
                EmailAddress.createValidEmailAddress(to),
                subject
        );
    }

    public EmailAddress getFrom() {
        return from;
    }

    public EmailAddress getTo() {
        return to;
    }

    public String getSubject() {
        return subject;
    }
}