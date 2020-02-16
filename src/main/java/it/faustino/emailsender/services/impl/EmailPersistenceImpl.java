package it.faustino.emailsender.services.impl;

import it.faustino.emailsender.models.EmailEntity;
import it.faustino.emailsender.repositories.EmailRepository;
import it.faustino.emailsender.services.EmailPersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class EmailPersistenceImpl implements EmailPersistence {
    private static final Logger log = LoggerFactory.getLogger(EmailPersistenceImpl.class);

    private final EmailRepository emailRepository;

    public EmailPersistenceImpl(EmailRepository emailRepository) {
        this.emailRepository = emailRepository;
    }

    @Override
    public CompletableFuture<Long> persistEmail(EmailEntity toPersist) {
        Objects.requireNonNull(toPersist, "toPersist cant be null");
        log.debug("saving mail to repo");

        return CompletableFuture
                .supplyAsync(() -> emailRepository.save(toPersist).getId())
                .whenComplete((persistedID, anError) -> {
                    if (anError != null) {
                        log.error("", anError);
                    } else {
                        log.debug("successfully persisted email {}:{}", persistedID, toPersist);
                    }
                });
    }

    @Override
    public List<EmailEntity> getAllEmails() {
        log.debug("fetching emails from repo");
        return StreamSupport
                .stream(emailRepository.findAll().spliterator(), false)
                .collect(Collectors.toUnmodifiableList());
    }

}
