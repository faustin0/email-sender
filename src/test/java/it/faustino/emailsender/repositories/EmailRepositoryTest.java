package it.faustino.emailsender.repositories;

import it.faustino.emailsender.models.EmailBuilder;
import it.faustino.emailsender.models.EmailEntity;
import it.faustino.emailsender.models.MailHeader;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureJdbc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

@AutoConfigureJdbc
@SpringBootTest(classes = {EmailRepositoryTestConfig.class})
@ExtendWith(SpringExtension.class)
@TestMethodOrder(OrderAnnotation.class)
class EmailRepositoryTest {

    @Autowired
    EmailRepository sut;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    @Order(1)
    void shouldGet_EmptyRepo() {
        Iterable<EmailEntity> emailEntities = sut.findAll();

        assertThat(emailEntities).isEmpty();
    }

    @Test
    @Order(2)
    void shouldInsert_and_getEmail() {
        var mailHeader = MailHeader.createMailHeader("a@b.com", "b@a.com", "an email");

        var toSave = EmailBuilder.builder()
                .mailHeader(mailHeader)
                .body("a text")
                .created(LocalDateTime.now())
                .build();

        EmailEntity saved = sut.save(toSave);

        Optional<EmailEntity> fetched = sut.findById(saved.getId().getAsLong());

        assertThat(saved.getId()).isNotNull();
        assertThat(fetched)
                .isPresent()
                .hasValueSatisfying(
                        emailEntity -> assertThat(emailEntity).hasNoNullFieldsOrProperties()
                );
    }

    @Test
    @Order(3)
    void shouldGetAllEmails() {
        Iterable<EmailEntity> all = sut.findAll();

        assertThat(all)
                .extracting(EmailEntity::getToEmailAddress)
                .extracting(emailAddress -> emailAddress.getAddress())
                .contains("b@a.com");
    }


}