package it.faustino.emailsender.services.impl;

import com.icegreen.greenmail.configuration.GreenMailConfiguration;
import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.GreenMailUtil;
import com.icegreen.greenmail.util.ServerSetupTest;
import it.faustino.emailsender.MailIntegrationTestConfiguration;
import it.faustino.emailsender.models.Email;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import javax.mail.internet.MimeMessage;
import java.util.concurrent.ExecutionException;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = {MailIntegrationTestConfiguration.class})
class EmailSenderImplTest {

    @Autowired
    EmailSenderImpl sut;

    @SpyBean
    JavaMailSender mailSender;

    public GreenMail greenMail;

    @BeforeEach
    void setUp() {
        greenMail = new GreenMail(ServerSetupTest.SMTP_POP3_IMAP)
                .withConfiguration(GreenMailConfiguration.aConfig().withDisabledAuthentication());
        greenMail.reset();
    }

    @AfterEach
    void tearDown() {
        greenMail.stop();
    }

    @Test
    void shouldSend_simpleMail() throws ExecutionException, InterruptedException {
        var toSend = new Email.Builder()
                .to("to@localhost.com")
                .from("me@localhost.com")
                .subject("Test Email")
                .body("some text from test!")
                .build();

        sut.sendSimpleMail(toSend).get();

        MimeMessage[] receivedMessages = greenMail.getReceivedMessages();

        String body = GreenMailUtil.getBody(receivedMessages[0]);

        assertThat(body).isEqualTo("some text from test!");
    }

    @Test
    void shouldSend_ExpectedMail() throws ExecutionException, InterruptedException {
        var toSend = new Email.Builder()
                .to("to@localhost.com")
                .from("me@localhost.com")
                .subject("Test Email")
                .body("some text from test!")
                .build();

        var expected = new SimpleMailMessage();
        expected.setTo("to@localhost.com");
        expected.setFrom("me@localhost.com");
        expected.setSubject("Test Email");
        expected.setText("some text from test!");

        sut.sendSimpleMail(toSend).get();
        Mockito.verify(mailSender).send(expected);
    }

    @Test
    void shouldGetError_simpleMail() throws ExecutionException, InterruptedException {
        var toSend = new Email.Builder()
                .to("to@localhost.com")
                .from("me@localhost.com")
                .subject("Test Email")
                .body("some text from test!")
                .build();

        sut.sendSimpleMail(toSend).get();

        MimeMessage[] receivedMessages = greenMail.getReceivedMessages();

        String body = GreenMailUtil.getBody(receivedMessages[0]);

        assertThat(body).isEqualTo("some text from test!");
    }
}