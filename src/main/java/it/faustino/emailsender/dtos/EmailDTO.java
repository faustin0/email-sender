package it.faustino.emailsender.dtos;

import org.springframework.lang.Nullable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class EmailDTO {
    @Nullable
    private Long id;

    @NotBlank(message = "from can't empty!")
    private String from;

    @NotBlank(message = "to can't empty!")
    private String to;

    @NotNull
    private String body;

    @NotBlank(message = "subject can't empty!")
    private String subject;

    public EmailDTO() {
    }

    public EmailDTO(String from, String to, String body) {
        this.from = from;
        this.to = to;
        this.body = body;
    }

    @Nullable
    public Long getID() {
        return id;
    }

    public void setID(@Nullable Long id) {
        this.id = id;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
