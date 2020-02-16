package it.faustino.emailsender.services;

import it.faustino.emailsender.models.EmailEntity;

import java.util.concurrent.CompletableFuture;

public interface EmailSender {

    CompletableFuture<Void> sendSimpleMail(EmailEntity toSend);
}
