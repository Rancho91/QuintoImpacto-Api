package com.semillero.ecosistema.utils;

import com.semillero.ecosistema.dtos.supplier.SupplierEmailDto;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.modelmapper.ModelMapper;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import java.util.List;

@Component
public class EmailUtil {
    private final SpringTemplateEngine templateEngine;
    private final JavaMailSender mailSender;
    private final ModelMapper modelMapper;

    public EmailUtil(SpringTemplateEngine templateEngine, JavaMailSender mailSender, ModelMapper modelMapper) {
        this.templateEngine = templateEngine;
        this.mailSender = mailSender;
        this.modelMapper = modelMapper;
    }

    private static final String email = "noreply@baeldung.com";


    public void sendEmailHtml(String[] to, String subject, List<SupplierEmailDto> emailDtoList) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(message, true);

        messageHelper.setFrom(email);
        messageHelper.setTo(to);
        messageHelper.setSubject(subject);

        Context context = new Context();
        context.setVariable("emailDtoList", emailDtoList);
        String htmlContent = templateEngine.process("email", context);
        messageHelper.setText(htmlContent, true);

        mailSender.send(message);
    }

    private InternetAddress mapToInternetAddress(String email) {
        return modelMapper.map(email, InternetAddress.class);
    }
}

