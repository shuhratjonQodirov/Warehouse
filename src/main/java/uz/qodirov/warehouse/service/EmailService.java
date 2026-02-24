package uz.qodirov.warehouse.service;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import uz.qodirov.warehouse.dto.res.SendMailDto;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender mailSender;
    private final Configuration freemarker;

    @Value("${spring.mail.username}")
    String sender;

    public void sendMail(SendMailDto dto) {

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper;
            helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());

            Template t = freemarker.getTemplate(dto.getTemplateName());

            String html = FreeMarkerTemplateUtils.processTemplateIntoString(t, dto.getModel());

            helper.setFrom("Ppd@gmail.com");
            helper.setTo(dto.getSendTo());
            helper.setSubject(dto.getSubjectName());
            helper.setText(html, true);
            mailSender.send(message);

        } catch (MessagingException | TemplateException | IOException e) {
            log.error(e.getMessage());
        }

    }
}