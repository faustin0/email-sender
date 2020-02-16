package it.faustino.emailsender.repositories;

import it.faustino.emailsender.models.EmailEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailRepository extends CrudRepository<EmailEntity, Long> {
}
