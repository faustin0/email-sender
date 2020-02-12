package it.faustino.emailsender.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.faustino.emailsender.dtos.EmailDTO;
import it.faustino.emailsender.models.Email;
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

import java.util.concurrent.CompletableFuture;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EmailController.class)
@ExtendWith(SpringExtension.class)
class EmailControllerTest {

    @Autowired
    MockMvc sut;

    @MockBean
    EmailSender emailSender;

    @Autowired
    ObjectMapper objectMapper;

    private EmailDTO sampleMail;

    @BeforeEach
    void setUp() {
        sampleMail = new EmailDTO();
        sampleMail.setFrom("from-field");
        sampleMail.setTo("to-field");
        sampleMail.setBody("body-field");
        sampleMail.setSubject("subject-field");

    }

    @Test
    void shouldRespond_withSuccess_whenEmailSent() throws Exception {
        String jsonContent = objectMapper.writeValueAsString(sampleMail);

        doReturn(CompletableFuture.allOf())
                .when(emailSender)
                .sendSimpleMail(any(Email.class));

        MvcResult mvcResult = sut.perform(post("/api/mails/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonContent))
                .andReturn();

        sut.perform(asyncDispatch(mvcResult))
                .andExpect(status().isOk())
                .andReturn();

        verify(emailSender).sendSimpleMail(any(Email.class));
    }

    @Test
    void shouldRespond_withError_whenEmailFail() throws Exception {
        String jsonContent = objectMapper.writeValueAsString(sampleMail);

        doReturn(CompletableFuture.failedFuture(new IllegalStateException("mock fail")))
                .when(emailSender)
                .sendSimpleMail(any(Email.class));

        MvcResult mvcResult = sut.perform(post("/api/mails/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonContent))
                .andReturn();

        sut.perform(asyncDispatch(mvcResult))
                .andExpect(status().is5xxServerError())
                .andReturn();

        verify(emailSender).sendSimpleMail(any(Email.class));
    }
}

