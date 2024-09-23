package com.demo.BankingApp.service.impl;

import com.demo.BankingApp.dto.ApiResponse;
import com.demo.BankingApp.dto.TransferData;
import com.demo.BankingApp.model.User;
import com.demo.BankingApp.repository.UserRepo;
import com.demo.BankingApp.service.TransferService;
import com.demo.BankingApp.service.UserService;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;


@Service
public class TrasnferServiceImpl implements TransferService {

    @Autowired
    UserService userService;

    @Autowired
    UserRepo userRepo;

    @Override
    public ApiResponse transfer(TransferData data){
        if (!userService.accountExists(data.getSenderAccountNumber())){
            return new ApiResponse(HttpStatus.NOT_FOUND.value(), "Sender account not found", null);
        }
        if (!userService.accountExists(data.getReceiverAccountNumber())){
            return new ApiResponse(HttpStatus.NOT_FOUND.value(), "Receiver account not found", null);
        }
        if (data.getSenderAccountNumber().equals(data.getReceiverAccountNumber())){
            return new ApiResponse(HttpStatus.BAD_REQUEST.value(), "Cannot transfer to own account", null);
        }

        User sender = userService.getUserByAccountNumber(data.getSenderAccountNumber());
        User receiver = userService.getUserByAccountNumber(data.getReceiverAccountNumber());

        BigDecimal amount = data.getAmount();

        if (!sender.hasSufficientFund(amount)){
            return new ApiResponse(HttpStatus.BAD_REQUEST.value(), "Insufficient fund", null);
        }

        sender.setAccountBalance(sender.getAccountBalance().subtract(amount)); // debit sender
        receiver.setAccountBalance(receiver.getAccountBalance().add(amount)); // credit receiver

        userRepo.save(sender);
        userRepo.save(receiver);

        // TODO: send debit and credit alerts to sender and receiver

        return new ApiResponse(
            HttpStatus.OK.value(), "Transfer successful ✅", null
        );
    }
}
