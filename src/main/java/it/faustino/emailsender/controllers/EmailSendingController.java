package it.faustino.emailsender.controllers;

import it.faustino.emailsender.dtos.EmailDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class EmailSendingController {

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("emailDTO", new EmailDTO());
        return "index";
    }

    @PostMapping("/sendEmail")
    public String greetingSubmit(@ModelAttribute  @Valid EmailDTO emailDTO) {
        return "index";
    }

}
