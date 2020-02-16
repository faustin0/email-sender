package it.faustino.emailsender.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.faustino.emailsender.dtos.EmailDTO;
import it.faustino.emailsender.models.EmailBuilder;
import it.faustino.emailsender.models.EmailEntity;
import it.faustino.emailsender.services.EmailPersistence;
import it.faustino.emailsender.services.EmailSender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EmailController.class)
@ExtendWith(SpringExtension.class)
class EmailControllerTest {

    @Autowired
    MockMvc sut;

    @MockBean
    EmailSender emailSender;

    @MockBean
    EmailPersistence emailPersistence;

    @Autowired
    ObjectMapper objectMapper;

    private EmailDTO sampleMail;
    private List<EmailEntity> mails;

    @BeforeEach
    void setUp() {
        sampleMail = new EmailDTO();
        sampleMail.setFrom("from-field");
        sampleMail.setTo("to-field");
        sampleMail.setBody("body-field");
        sampleMail.setSubject("subject-field");

        var email = EmailBuilder.builder()
                .to("a@b.com")
                .sender("b@a.com")
                .subject("subjet")
                .body("text")
                .created(LocalDateTime.now())
                .build();

        mails = List.of(email, email);
    }

    @Test
    void shouldRespond_withSuccess_whenEmailSent() throws Exception {
        String jsonContent = objectMapper.writeValueAsString(sampleMail);

        doReturn(CompletableFuture.completedFuture(1L))
                .when(emailPersistence)
                .persistEmail(any(EmailEntity.class));

        doReturn(CompletableFuture.allOf())
                .when(emailSender)
                .sendSimpleMail(any(EmailEntity.class));

        MvcResult mvcResult = sut.perform(post("/api/mails/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonContent))
                .andReturn();

        sut.perform(asyncDispatch(mvcResult))
                .andExpect(status().isOk())
                .andReturn();

        verify(emailSender).sendSimpleMail(any(EmailEntity.class));
    }

    @Test
    void shouldRespond_withError_whenEmailFail() throws Exception {
        String jsonContent = objectMapper.writeValueAsString(sampleMail);

        doReturn(CompletableFuture.completedFuture(1L))
                .when(emailPersistence)
                .persistEmail(any(EmailEntity.class));

        doReturn(CompletableFuture.failedFuture(new IllegalStateException("mock fail")))
                .when(emailSender)
                .sendSimpleMail(any(EmailEntity.class));

        MvcResult mvcResult = sut.perform(post("/api/mails/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonContent))
                .andReturn();

        sut.perform(asyncDispatch(mvcResult))
                .andExpect(status().is5xxServerError())
                .andReturn();

        verify(emailSender).sendSimpleMail(any(EmailEntity.class));
    }

    @Test
    void shouldBeError_butDBFails() throws Exception {
        String jsonContent = objectMapper.writeValueAsString(sampleMail);

        doReturn(CompletableFuture.failedFuture(new IllegalStateException("mock db fail")))
                .when(emailPersistence)
                .persistEmail(any(EmailEntity.class));

        MvcResult mvcResult = sut.perform(post("/api/mails/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonContent))
                .andReturn();

        sut.perform(asyncDispatch(mvcResult))
                .andExpect(status().is5xxServerError())
                .andReturn();

        verify(emailPersistence).persistEmail(any(EmailEntity.class));
    }

    @Test
    void shouldBeFailure_whenMailFail_butDBisOk() throws Exception {
        String jsonContent = objectMapper.writeValueAsString(sampleMail);

        doReturn(CompletableFuture.completedFuture(1L))
                .when(emailPersistence)
                .persistEmail(any(EmailEntity.class));

        doReturn(CompletableFuture.failedFuture(new IllegalStateException("mock email fail")))
                .when(emailSender)
                .sendSimpleMail(any(EmailEntity.class));

        MvcResult mvcResult = sut.perform(post("/api/mails/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonContent))
                .andReturn();

        sut.perform(asyncDispatch(mvcResult))
                .andExpect(status().is5xxServerError())
                .andReturn();

        verify(emailSender).sendSimpleMail(any(EmailEntity.class));
        verify(emailPersistence).persistEmail(any(EmailEntity.class));
    }

    @Test
    void shouldGetAllEmails() throws Exception {
        doReturn(mails)
                .when(emailPersistence)
                .getAllEmails();

        MvcResult mvcResult = sut.perform(get("/api/mails/")
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        sut.perform(asyncDispatch(mvcResult))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.[0].from").value("b@a.com"))
                .andExpect(jsonPath("$.[0].to").value("a@b.com"))
                .andExpect(jsonPath("$.[1].body").value("text"))
                .andExpect(jsonPath("$.[1].subject").value("subjet"));

        verify(emailPersistence, description("only one call")).getAllEmails();
    }
}

