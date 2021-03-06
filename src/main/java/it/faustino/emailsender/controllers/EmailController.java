package it.faustino.emailsender.controllers;

import it.faustino.emailsender.dtos.EmailDTO;
import it.faustino.emailsender.models.EmailBuilder;
import it.faustino.emailsender.models.EmailEntity;
import it.faustino.emailsender.models.MailHeader;
import it.faustino.emailsender.services.EmailPersistence;
import it.faustino.emailsender.services.EmailSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/mails")
public class EmailController {

    private static final Logger log = LoggerFactory.getLogger(EmailController.class);

    private final EmailSender emailSender;
    private final EmailPersistence emailPersistence;

    public EmailController(EmailSender emailSender, EmailPersistence emailPersistence) {
        this.emailSender = emailSender;
        this.emailPersistence = emailPersistence;
    }

    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public CompletableFuture<ResponseEntity<String>> newMail(@RequestBody @Valid EmailDTO emailData) {
        EmailEntity toSend = emailDTOtoEmail(emailData);

        return emailPersistence
                .persistEmail(toSend)
                .thenCompose(emailId -> emailSender
                        .sendSimpleMail(toSend)
                        .thenApply(success -> emailId)
                )
                .whenComplete(this::logStatus)
                .thenApply(emailId -> ResponseEntity.ok(String.format("{\"%s\":%d}", "emailId", emailId)))
                .exceptionally(throwable -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
    }

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public CompletableFuture<ResponseEntity<Collection<EmailDTO>>> getAllMails() {
        return CompletableFuture
                .supplyAsync(emailPersistence::getAllEmails)
                .thenApply(this::toEmailDTOS)
                .thenApply(ResponseEntity::ok);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CompletableFuture<ResponseEntity<EmailDTO>> getMail(@PathVariable("id") long mailId) {
        return emailPersistence
                .getEmail(mailId)
                .thenApply(emailEntity -> emailEntity.map(EmailDTO::fromEmail))
                .thenApply(ResponseEntity::of);
    }

    private void logStatus(Long emailID, Throwable anError) {
        if (anError != null) {
            log.error("failure dispatching request", anError);
        } else {
            log.info("successfully dispatched request, emailId={}", emailID);
        }
    }

    private EmailEntity emailDTOtoEmail(EmailDTO emailDTO) {
        return EmailBuilder.builder()
                .mailHeader(MailHeader.createMailHeader(
                        emailDTO.getFrom(),
                        emailDTO.getTo(),
                        emailDTO.getSubject()
                ))
                .body(emailDTO.getBody().trim())
                .created(LocalDateTime.now())
                .build();
    }


    private List<EmailDTO> toEmailDTOS(List<EmailEntity> emails) {
        return emails.stream()
                .map(EmailDTO::fromEmail)
                .collect(Collectors.toUnmodifiableList());
    }
}
