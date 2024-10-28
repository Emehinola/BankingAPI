package com.demo.BankingApp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.demo.BankingApp.dto.EmailDetails;

import com.demo.BankingApp.service.EmailService;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;


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

    @Override
    public void sendEmailWithAttachment(EmailDetails details) throws MailException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper;

        try{
            helper = new MimeMessageHelper(mimeMessage, true); // true to file
            helper.setFrom(emailUser);
            helper.setTo(details.getReceipient());
            helper.setSubject(details.getSubject());
            helper.setText(details.getMessage());

            FileSystemResource file = new FileSystemResource(details.getAttachment());
            helper.addAttachment(file.getFilename(), file);
            javaMailSender.send(mimeMessage);

        } catch(MessagingException exception){
            System.out.println("Exception occured");
        }
    }

}
