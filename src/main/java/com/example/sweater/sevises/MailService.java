package com.example.sweater.sevises;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class MailService {
    private final JavaMailSender mailSender;

    @Value("${spring.mail.email}")
    private String email;

    @Autowired
    public MailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    // отправляет письмо, содержащее простой текст (text/plain)
//    public void send(String sendTo, String subject, String message) {
//        SimpleMailMessage mailMessage = new SimpleMailMessage();
//
//        mailMessage.setFrom(email);
//        mailMessage.setTo(sendTo);
//        mailMessage.setSubject(subject);
//        mailMessage.setText(message);
//        mailSender.send(mailMessage);
//    }

    // отправляет письмо, поддерживающее HTML разметку (text/html)
    void send(String sendTo, String subject, String message) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        mimeMessage.setFrom(email);
        mimeMessage.setContent(message, "text/html; charset=utf-8");

        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, false);
        helper.setTo(sendTo);
        helper.setSubject(subject);

        mailSender.send(mimeMessage);
    }
}
