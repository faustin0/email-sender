package it.faustino.emailsender.services;

import it.faustino.emailsender.models.Email;

import java.util.concurrent.CompletableFuture;

public interface EmailPersistence {
    CompletableFuture<Long> persistEmail(Email toPersist);

//    List<Email> getEmails(); TODO
}
