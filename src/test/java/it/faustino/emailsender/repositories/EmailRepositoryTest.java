package it.faustino.emailsender.repositories;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
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
    void shouldGet_EmptyRepo() {
        Iterable<EmailEntity> emailEntities = sut.findAll();

        assertThat(emailEntities).isEmpty();
    }

    @Test
    void shouldInsert_and_getEmail() {
        var toSave = new EmailEntity();
        toSave.body = "a text";
        toSave.created = LocalDateTime.now();
        toSave.sender = "a@b.com";
        toSave.subject = "an email";
        toSave.to = "b@a.com";

        EmailEntity saved = sut.save(toSave);

        Iterable<EmailEntity> all = sut.findAll();

        assertThat(saved.id).isNotNull();
        assertThat(all)
                .extracting(emailEntity -> emailEntity.to)
                .contains("b@a.com");
    }


}