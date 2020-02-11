package it.faustino.emailsender;

import it.faustino.emailsender.services.EmailSender;
import it.faustino.emailsender.services.impl.EmailSenderImpl;
import org.hibernate.validator.constraints.Email;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@SpringBootApplication
public class EmailSenderApplication {

    public static void main(String[] args) {
        SpringApplication.run(EmailSenderApplication.class, args);
    }

    @Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("localhost");
        mailSender.setPort(3025);
        mailSender.setUsername("my.gmail@gmail.com");
        mailSender.setPassword("password");

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");

        return mailSender;
    }

    @Bean
    public EmailSender geMailService(JavaMailSender sender) {
        return new EmailSenderImpl(sender);
    }
}
