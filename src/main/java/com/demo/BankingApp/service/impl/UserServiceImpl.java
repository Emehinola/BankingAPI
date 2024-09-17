package com.demo.BankingApp.service.impl;

import java.math.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.demo.BankingApp.dto.AccountInfo;
import com.demo.BankingApp.dto.CreditDebitRequest;
import com.demo.BankingApp.dto.ApiResponse;
import com.demo.BankingApp.dto.EmailDetails;
import com.demo.BankingApp.dto.UserRequest;
import com.demo.BankingApp.model.User;
import com.demo.BankingApp.repository.UserRepo;
import com.demo.BankingApp.utils.AccountUtils;


@Service
public class UserServiceImpl implements UserService {

    AccountUtils accountUtils;

    @Autowired
    UserRepo repo;
    
    @Autowired
    EmailService emailService;

    @Override
    public ApiResponse createAccount(UserRequest request){

        if(repo.existsByEmail(request.getEmail())){
            return new ApiResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Account already exists",
                null
            );

        }

        User user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .otherName(request.getOtherName())
                .gender(request.getGender())
                .address(request.getAddress())
                .stateOfOrigin(request.getStateOfOrigin())
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .altPhoneNumber(request.getAltPhoneNumber())
                .accountNumber(AccountUtils.generateAccountNo())
                .accountBalance(BigDecimal.ZERO)
                .status("ACTIVE")
            .build();

            User savedUser = repo.save(user); // save user

            AccountInfo info = AccountInfo.builder()
                .accountBalance(savedUser.getAccountBalance())
                .accountNumber(savedUser.getAccountNumber())
                .accountName(savedUser.getFullName())
            .build();

            // set email alert
            StringBuilder builder = new StringBuilder();
            builder.append("Congratulations! You have successfully created an account with us. Let's get started!\nAccount Details:");
            builder.append("Account Name: " + user.getFullName());
            builder.append("Account Number: " + user.getAccountNumber());
            builder.append("Account Balance: " + user.getAccountBalance());

            EmailDetails emailDetails = EmailDetails.builder()
                .receipient(user.getEmail())
                .subject("Account creation successful!")
                .message(builder.toString())
            .build();
            emailService.sendEmailAlert(emailDetails);

            ApiResponse response = ApiResponse.builder()
                .code(HttpStatus.OK.value())
                .message("Account created successfully!")
                .data(info)
            .build();

            return response;
    }

    public ApiResponse deleteAccount(Long id){
        repo.deleteById(id); // delete account
        ApiResponse response = ApiResponse.builder()
                .code(HttpStatus.OK.value())
                .message("Account deleted successfully!")
                .data(null)
            .build();

            return response;
    }

    public ApiResponse balanceEnquiry(String accountNumber){

        if(repo.existsByAccountNumber(accountNumber)){
            User user = repo.findByAccountNumber(accountNumber);
            AccountInfo info = AccountInfo.builder()
                .accountBalance(user.getAccountBalance())
                .accountName(user.getFullName())
                .accountNumber(user.getAccountNumber())
            .build();
            
            return ApiResponse.builder()
                    .code(HttpStatus.OK.value())
                    .message("Balance enquiry")
                    .data(info)
                .build();
        }

        return ApiResponse.builder()
                    .code(HttpStatus.NOT_FOUND.value())
                    .message("Account does not exist")
                    .data(null)
                .build();
    }

    @Override
    public ApiResponse creditAccount(CreditDebitRequest request){

        if(!repo.existsByAccountNumber(request.getAccountNumber())){
            return ApiResponse.builder()
                    .code(HttpStatus.NOT_FOUND.value())
                    .message("Account does not exist")
                    .data(null)
                .build();
        }

        // get user
        User user = repo.findByAccountNumber(request.getAccountNumber());

        // credit operation
        user.setAccountBalance(user.getAccountBalance().add(request.getAmount()));
        repo.save(user); // save user

        AccountInfo info = AccountInfo.builder()
                .accountBalance(user.getAccountBalance())
                .accountName(user.getFullName())
                .accountNumber(user.getAccountNumber())
            .build();
        
        return ApiResponse.builder()
            .code(HttpStatus.OK.value())
            .message("Account successfully credited")
            .data(info)
        .build();
    }

    @Override
    public ApiResponse debitAccount(CreditDebitRequest request){

        if(!repo.existsByAccountNumber(request.getAccountNumber())){
            return ApiResponse.builder()
                    .code(HttpStatus.NOT_FOUND.value())
                    .message("Account does not exist")
                    .data(null)
                .build();
        }
        

        // get user
        User user = repo.findByAccountNumber(request.getAccountNumber());

        if (!user.hasSufficientFund(request.getAmount())){
            return ApiResponse.builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .message("Insufficient fund")
                .data(null)
            .build();
        }

        // debit operation
        user.setAccountBalance(user.getAccountBalance().subtract(request.getAmount()));

        AccountInfo info = AccountInfo.builder()
            .accountBalance(user.getAccountBalance())
            .accountName(user.getFullName())
            .accountNumber(user.getAccountNumber())
        .build();

        repo.save(user); // save user
        
        return ApiResponse.builder()
            .code(HttpStatus.OK.value())
            .message("Account successfully debited")
            .data(info)
        .build();
    }
}
