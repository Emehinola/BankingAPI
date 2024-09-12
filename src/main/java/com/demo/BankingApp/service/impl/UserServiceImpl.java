package com.demo.BankingApp.service.impl;

import java.math.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.demo.BankingApp.dto.AccountInfo;
import com.demo.BankingApp.dto.ApiResponse;
import com.demo.BankingApp.dto.UserRequest;
import com.demo.BankingApp.model.User;
import com.demo.BankingApp.repository.UserRepo;
import com.demo.BankingApp.utils.AccountUtils;


@Service
public class UserServiceImpl implements UserService {

    AccountUtils accountUtils;

    @Autowired
    UserRepo repo;

    @Override
    public ApiResponse createAccount(UserRequest request){

        if(repo.existsByEmail(request.getEmail())){
            return new ApiResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Account already exists",
                null
            );

        }

        // account creation process

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

            ApiResponse response = ApiResponse.builder()
                .code(HttpStatus.OK.value())
                .message("Account created successfully!")
                .data(info)
            .build();

            return response;
    }
}
