package it.faustino.emailsender.services.impl;

import it.faustino.emailsender.models.EmailBuilder;
import it.faustino.emailsender.models.EmailEntity;
import it.faustino.emailsender.models.MailHeader;
import it.faustino.emailsender.repositories.EmailRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
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
        var mailHeader = MailHeader.createMailHeader("a@b.com", "b@a.com", "an email");

        var email = EmailBuilder.builder()
                .id(1L)
                .mailHeader(mailHeader)
                .body("some text")
                .created(LocalDateTime.now())
                .build();

        var emailNoBody = EmailBuilder.builder()
                .mailHeader(mailHeader)
                .body("")
                .created(LocalDateTime.now())
                .build();

        var emails = List.of(email, email, emailNoBody, emailNoBody);

        Mockito.doReturn(emails)
                .when(repository)
                .findAll();

        List<EmailEntity> allEmails = sut.getAllEmails();
        assertThat(allEmails)
                .isNotEmpty()
                .doesNotContainNull()
                .allSatisfy(e -> assertThat(e).hasNoNullFieldsOrPropertiesExcept("id"));
    }
}