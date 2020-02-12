package it.faustino.emailsender;

import it.faustino.emailsender.services.impl.EmailSenderImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {EmailSenderImpl.class})
class EmailSenderApplicationTests {

    @Autowired
    EmailSenderApplication application;

    @Test
    void contextLoads() {
        Assertions.assertThat(application)
                .as("context load test")
                .isNotNull();
    }

}
