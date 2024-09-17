package com.demo.BankingApp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.demo.BankingApp.dto.EmailDetails;


@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}") // reads value from application.properties
    private String emailUser;

    @Override
    public void sendEmailAlert(EmailDetails details){
        try{

            SimpleMailMessage message = new SimpleMailMessage();

            message.setFrom(emailUser);
            message.setTo(details.getReceipient());
            message.setText(details.getMessage());
            message.setSubject(details.getSubject());

            javaMailSender.send(message);
            System.out.println("Message sent successfully ✅");
            
        }catch(MailException e){
            System.out.println("Exception: " + e.toString());
        }
    }

}
