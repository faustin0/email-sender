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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.mail.internet.MimeMessage;
import java.util.concurrent.ExecutionException;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = {MailIntegrationTestConfiguration.class})
class EmailSenderImplTest {

    @Autowired
    EmailSenderImpl sut;

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
        Email toSend = new Email.Builder()
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