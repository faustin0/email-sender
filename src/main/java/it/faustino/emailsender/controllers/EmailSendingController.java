package it.faustino.emailsender.controllers;

import it.faustino.emailsender.dtos.EmailDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class EmailSendingController {

    private static final Logger log = LoggerFactory.getLogger(EmailSendingController.class);

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("emailDTO", new EmailDTO());
        return "index";
    }

    @PostMapping("/sendEmail")
    public String greetingSubmit(@ModelAttribute @Valid EmailDTO emailDTO) {
        log.debug("getting mail to send");
        return "index";
    }

}
