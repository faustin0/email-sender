package it.faustino.emailsender.services.impl;

import it.faustino.emailsender.MailIntegrationTestConfiguration;
import it.faustino.emailsender.models.EmailBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;

@SpringBootTest(classes = {MailIntegrationTestConfiguration.class})
class EmailSenderImplFailureTest {

    @Autowired
    EmailSenderImpl sut;

    @MockBean
    JavaMailSender failingSender;

    @BeforeEach
    void setUp() {
    }


    @Test
    void shouldGetError_simpleMail() {
        var toSend = EmailBuilder.builder()
                .body("")
                .created(LocalDateTime.now())
                .sender("")
                .subject("")
                .to("")
                .build();

        doThrow(MailSendException.class)
                .when(failingSender)
                .send(any(SimpleMailMessage.class));

        assertThatCode(() -> sut.sendSimpleMail(toSend).get())
                .hasCauseInstanceOf(MailSendException.class);//todo use custom
    }
}