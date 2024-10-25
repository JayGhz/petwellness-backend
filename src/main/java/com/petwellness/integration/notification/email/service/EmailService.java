package com.petwellness.integration.notification.email.service;

import com.petwellness.integration.notification.email.dto.Mail;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.io.ByteArrayInputStream;
import java.util.Map;


@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;

    public Mail createMail(String to, String subject, Map<String, Object> model, String from) {
        Mail mail = new Mail();
        mail.setFrom(from);
        mail.setTo(to);
        mail.setSubject(subject);
        mail.setModel(model);
        return mail;
    }

    public void sendMail(Mail mail, String templateName) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message,
                MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, "UTF-8");

        //Config el contexto de Thymelead con los datos del modelo
        Context context = new Context();
        context.setVariables(mail.getModel());

        //procesar la planttilla usando thymeleaf
        String html = templateEngine.process(templateName, context);
        helper.setTo(mail.getTo());
        helper.setText(html, true);
        helper.setSubject(mail.getSubject());
        helper.setFrom(mail.getFrom());

        //si necesitas agregar archivos adjuntos
        //helper.addAttachment("MyTestFile.txt", new ByteArrayResource("test".getBytes()));

        mailSender.send(message);

    }
}
