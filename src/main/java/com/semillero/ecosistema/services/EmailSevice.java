package com.semillero.ecosistema.services;



import com.semillero.ecosistema.dtos.supplier.SupplierEmailDto;
import com.semillero.ecosistema.utils.EmailUtil;
import jakarta.mail.MessagingException;

import jakarta.mail.internet.MimeMessage;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.File;
import java.io.IOException;
import java.util.List;


@Service
public class EmailSevice {
    private final SupplierService supplierService;
    private final UserService userService;
    private final EmailUtil emailUtil;

    public EmailSevice(SupplierService supplierService, UserService userService, EmailUtil emailUtil) {
        this.supplierService = supplierService;
        this.userService = userService;
        this.emailUtil = emailUtil;
    }

    //faltaria separar los mails de usuarios y administradores
    @Scheduled(cron = "0 0 9 ? * MON")
    public void sendEmails() throws MessagingException {
        try {
            List<String> listEmails = userService.getUsersEmails();
            List<SupplierEmailDto> emailDtoList = supplierService.getSuppliersLastWeek();
            System.out.println(listEmails);
            String[] to = listEmails.toArray(new String[0]);
            emailUtil.sendEmailHtml(to, "Novedades", emailDtoList);
        }catch (MessagingException e){
            throw new MessagingException(e.getMessage());
        }

    }
    //para usarla hay que configurar en el .env el mail emisor y la contraseña de aplicacion de google
    //dejo el controller para la demo






}
