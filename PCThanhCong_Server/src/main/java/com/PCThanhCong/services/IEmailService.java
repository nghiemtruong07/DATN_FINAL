package com.PCThanhCong.services;
import javax.mail.MessagingException;
public interface IEmailService {
    void send(String subject, String content, String to, Boolean isHtmlFormat) throws MessagingException;
}
