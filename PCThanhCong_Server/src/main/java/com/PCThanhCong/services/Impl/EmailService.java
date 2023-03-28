package com.PCThanhCong.services.Impl;

import com.PCThanhCong.services.IEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Component
@Service
public class EmailService implements IEmailService {
    @Autowired
    private JavaMailSender mailSender;

    @Override
    public void send(String subject, String content, String to, Boolean isHtmlFormat) throws MessagingException {
        if (isHtmlFormat == null) {
            isHtmlFormat = false;
        }
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");

        helper.setSubject(subject);
        helper.setText(content, isHtmlFormat);
        helper.setTo(to);

        mailSender.send(mimeMessage);
    }
}
