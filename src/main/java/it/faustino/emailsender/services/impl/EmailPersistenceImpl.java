package it.faustino.emailsender.services.impl;

import it.faustino.emailsender.models.Email;
import it.faustino.emailsender.repositories.EmailEntity;
import it.faustino.emailsender.repositories.EmailRepository;
import it.faustino.emailsender.services.EmailPersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class EmailPersistenceImpl implements EmailPersistence {
    private static final Logger log = LoggerFactory.getLogger(EmailPersistenceImpl.class);

    private final EmailRepository emailRepository;

    public EmailPersistenceImpl(EmailRepository emailRepository) {
        this.emailRepository = emailRepository;
    }

    @Override
    public CompletableFuture<Long> persistEmail(Email toPersist) {
        EmailEntity emailEntity = EmailEntity.fromEmail(toPersist);

        return CompletableFuture
                .supplyAsync(() -> emailRepository.save(emailEntity).getId())
                .whenComplete((persistedID, anError) -> {
                    if (anError != null) {
                        log.error("", anError);
                    } else {
                        log.debug("successfully persisted email {}:{}", persistedID, toPersist);
                    }
                })
                ;
    }

}
