package it.faustino.emailsender.repositories;

import it.faustino.emailsender.models.EmailBuilder;
import it.faustino.emailsender.models.EmailEntity;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureJdbc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;

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

        EmailEntity toSave = EmailBuilder.builder()
                .body("a text")
                .created(LocalDateTime.now())
                .sender("a@b.com")
                .subject("an email")
                .to("b@a.com")
                .build();

        EmailEntity saved = sut.save(toSave);
        EmailEntity fetched = sut.findById(1L).get();

        Iterable<EmailEntity> all = sut.findAll();

        assertThat(saved.getId()).isNotNull();
        assertThat(all)
                .extracting(EmailEntity::getTo)
                .contains("b@a.com");
    }


}