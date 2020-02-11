package it.faustino.emailsender.services;

import it.faustino.emailsender.models.Email;

import java.util.concurrent.CompletableFuture;

public interface EmailSender {

    CompletableFuture<Void> sendSimpleMail(Email toSend);
}
