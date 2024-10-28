package com.demo.BankingApp.service;

import com.demo.BankingApp.dto.EmailDetails;

public interface EmailService {

    public void sendEmailAlert(EmailDetails details);
    public void sendEmailWithAttachment(EmailDetails details);

}
