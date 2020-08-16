package com.sherwin.ebook.email;

import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class EmailController {

    @Autowired
    private EmailService emailService;

    @GetMapping("/email")
    public String sendEmail(Model model) {
        try {
            JsonNode node = emailService.sendSimpleMessage();
            model.addAttribute("node",node.toString());
        } catch (UnirestException e) {
            e.printStackTrace();
        }
        return "index";
    }
}
