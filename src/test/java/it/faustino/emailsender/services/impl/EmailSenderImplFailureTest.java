package it.faustino.emailsender.services.impl;

import it.faustino.emailsender.MailIntegrationTestConfiguration;
import it.faustino.emailsender.models.Email;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.util.concurrent.CompletableFuture;

import static org.assertj.core.api.Assertions.assertThat;
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
        var toSend = new Email.Builder().build();

        doThrow(MailSendException.class)
                .when(failingSender)
                .send(any(SimpleMailMessage.class));

        CompletableFuture<Void> outcome = sut.sendSimpleMail(toSend);

        assertThat(outcome)
                .hasFailedWithThrowableThat()
                .isInstanceOf(MailSendException.class);//todo use custom
    }
}