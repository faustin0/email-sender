package it.faustino.emailsender.controllers;

import it.faustino.emailsender.dtos.EmailDTO;
import it.faustino.emailsender.models.Email;
import it.faustino.emailsender.services.EmailSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/mails")
public class EmailController {

    private static final Logger log = LoggerFactory.getLogger(EmailController.class);

    private final EmailSender emailSender;

    public EmailController(EmailSender emailSender) {
        this.emailSender = emailSender;
    }

    @ResponseBody
    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public CompletableFuture<ResponseEntity<String>> newMail(@RequestBody @Valid EmailDTO emailData) {
        Email toSend = emailMapper(emailData);
        return emailSender
                .sendSimpleMail(toSend)
                .whenCompleteAsync(this::logStatus)
                .thenApplyAsync(success -> ResponseEntity.ok("{}"))
                .exceptionally(error -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
    }

    private void logStatus(Void ignored, Throwable anError) {
        if (anError != null) {
            log.error("", anError);
        } else {
            log.debug("successfully dispatched request");
        }
    }

    private Email emailMapper(EmailDTO emailDTO) {
        return new Email.Builder()
                .from(emailDTO.getFrom().trim())
                .to(emailDTO.getTo().trim())
                .body(emailDTO.getBody().trim())
                .subject(emailDTO.getSubject().trim())
                .build();
    }
}
