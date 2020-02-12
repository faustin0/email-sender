package it.faustino.emailsender.models;

import static java.util.Objects.requireNonNull;

public class Email {
    private String from;
    private String to;
    private String body;
    private String subject;

    public String getFrom() {
        return from;
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

    private Email() {
    }

    @Override
    public String toString() {
        return "Email{" +
                "from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", body='" + body + '\'' +
                ", subject='" + subject + '\'' +
                '}';
    }

    public static class Builder {

        private final Email toBuild;

        public Builder() {
            this.toBuild = new Email();
        }

        public Builder from(String from) {
            toBuild.from = requireNonNull(from, "from null").trim();
            return this;
        }

        public Builder to(String to) {
            toBuild.to = requireNonNull(to, "to null").trim();
            return this;
        }

        public Builder body(String body) {
            toBuild.body = requireNonNull(body, "body null");
            return this;
        }

        public Builder subject(String subject) {
            toBuild.subject = requireNonNull(subject, "subject null").trim();
            return this;
        }

        public Email build() {
            return toBuild;
        }
    }
}
