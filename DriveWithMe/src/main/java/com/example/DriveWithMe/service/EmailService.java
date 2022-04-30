package com.example.DriveWithMe.service;

import com.example.DriveWithMe.model.ConfirmationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    @Autowired
    private final JavaMailSender emailSender;

    public EmailService(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }
    public void sendEmailForVerification(ConfirmationToken token) throws MailException {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(token.getUser().getEmail());
        mail.setFrom("drivewithmeapplication@gmail.com");
        mail.setSubject("Confirm your email");
        mail.setText("To activate your account press this link: " + "http://localhost:8080/api/users/activate?token=" + token.getToken());
        emailSender.send(mail);
    }
}
