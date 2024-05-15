package com.semillero.ecosistema.controllers;

import com.semillero.ecosistema.services.EmailSevice;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmailController {

    private final EmailSevice emailSevice;

    public EmailController(EmailSevice emailSevice) {
        this.emailSevice = emailSevice;
    }

    @PostMapping("/sendMail")
    public ResponseEntity<?> sendEmail() throws Exception {
        try{
            emailSevice.sendEmails();
            return new ResponseEntity<>(true, HttpStatus.OK);
        }  catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}
