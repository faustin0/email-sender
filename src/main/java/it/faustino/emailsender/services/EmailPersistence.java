package it.faustino.emailsender.services;

import it.faustino.emailsender.models.EmailEntity;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public interface EmailPersistence {
    CompletableFuture<Long> persistEmail(EmailEntity toPersist);

    CompletableFuture<Optional<EmailEntity>> getEmail(long id);

    List<EmailEntity> getAllEmails();
}
