package it.faustino.emailsender.services.impl;

import it.faustino.emailsender.models.Email;
import it.faustino.emailsender.repositories.EmailEntity;
import it.faustino.emailsender.repositories.EmailRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class EmailPersistenceImplTest {

    @Mock
    EmailRepository repository;

    private EmailPersistenceImpl sut;

    @BeforeEach
    void setUp() {
        sut = new EmailPersistenceImpl(repository);
    }

    @Test
    void shouldGetAllEmails() {
        var email = new Email.Builder()
                .subject("sogg")
                .body("some text")
                .to("a@b.com")
                .from("b@a.com")
                .build();

        var emailNoBody = new Email.Builder()
                .subject("sogg")
                .to("a@b.com")
                .from("b@a.com")
                .build();

        var emailEntity = EmailEntity.fromEmail(email);
        var emailEntityNoBody = EmailEntity.fromEmail(emailNoBody);
        var emails = List.of(emailEntity, emailEntity, emailEntityNoBody, emailEntityNoBody);

        Mockito.doReturn(emails)
                .when(repository)
                .findAll();

        List<Email> allEmails = sut.getAllEmails();
        assertThat(allEmails)
                .isNotEmpty()
                .doesNotContainNull()
                .allSatisfy(e -> assertThat(e).hasNoNullFieldsOrProperties());
    }
}