package com.demo.BankingApp.service.impl;

import com.demo.BankingApp.dto.BaseResponse;
import com.demo.BankingApp.dto.TransferData;
import com.demo.BankingApp.model.Transaction;
import com.demo.BankingApp.model.User;
import com.demo.BankingApp.repository.TransactionRepo;
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

    @Autowired
    TransactionRepo transactionRepo;

    @Override
    public BaseResponse transfer(TransferData data){
        if (!userService.accountExists(data.getSenderAccountNumber())){
            return new BaseResponse(HttpStatus.NOT_FOUND.value(), "Sender account not found", null);
        }
        if (!userService.accountExists(data.getReceiverAccountNumber())){
            return new BaseResponse(HttpStatus.NOT_FOUND.value(), "Receiver account not found", null);
        }
        if (data.getSenderAccountNumber().equals(data.getReceiverAccountNumber())){
            return new BaseResponse(HttpStatus.BAD_REQUEST.value(), "Cannot transfer to own account", null);
        }

        User sender = userService.getUserByAccountNumber(data.getSenderAccountNumber());
        User receiver = userService.getUserByAccountNumber(data.getReceiverAccountNumber());

        BigDecimal amount = data.getAmount();

        if (!sender.hasSufficientFund(amount)){
            return new BaseResponse(HttpStatus.BAD_REQUEST.value(), "Insufficient fund", null);
        }

        // sender transaction
        sender.setAccountBalance(sender.getAccountBalance().subtract(amount)); // debit sender
        Transaction senderTrxn = Transaction.builder()
            .amount(amount)
            .description("Transfer of fund")
            .accountNumber(sender.getAccountNumber())
            .transactionType("debit")
        .build();
        transactionRepo.save(senderTrxn);

        // reciever transaction
        receiver.setAccountBalance(receiver.getAccountBalance().add(amount)); // credit receiver
        Transaction receiverTrnx = Transaction.builder()
            .amount(amount)
            .description("Transfer of fund")
            .accountNumber(receiver.getAccountNumber())
            .transactionType("credit")
        .build();
        transactionRepo.save(receiverTrnx);

        userRepo.save(sender);
        userRepo.save(receiver);

        // TODO: send debit and credit alerts to sender and receiver

        return new BaseResponse(
            HttpStatus.OK.value(), "Transfer successful ✅", null
        );
    }
}
