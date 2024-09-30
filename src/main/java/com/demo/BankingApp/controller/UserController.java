package com.demo.BankingApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.BankingApp.dto.BaseResponse;
import com.demo.BankingApp.dto.CreditDebitRequest;
import com.demo.BankingApp.dto.TransferData;
import com.demo.BankingApp.dto.UserRequest;
import com.demo.BankingApp.service.TransferService;
import com.demo.BankingApp.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/user")
@Tag(name = "User Controller")
public class UserController {
    
    @Autowired
    UserService service;

    @Autowired
    TransferService transferService;

    @Operation(
        summary = "Account creation",
        description = "This is account creation endpoint"
    )
    @ApiResponse(
        responseCode = "201",
        description = "Success response"
    )
    @PostMapping("/")
    public BaseResponse createAccount(@RequestBody UserRequest request){
        return service.createAccount(request);
    }

    @Operation(
        summary = "Account deletion",
        description = "This endpoint deletes a user's account"
    )
    @ApiResponse(
        responseCode = "200",
        description = "Success response"
    )
    @DeleteMapping("/{userId}")
    public BaseResponse deleteAccount(@PathVariable Long userId){
        return service.deleteAccount(userId);
    }

    @Operation(
        summary = "Get account balance",
        description = "This endpoint gets the account balance of the provided account"
    )
    @ApiResponse(
        responseCode = "200",
        description = "Success response"
    )
    @GetMapping("/balance/{accountNumber}")
    public BaseResponse getBalance(@PathVariable String accountNumber){
        return service.balanceEnquiry(accountNumber);
    }

    @Operation(
        summary = "Credit account",
        description = "Endpoint to credit account"
    )
    @ApiResponse(
        responseCode = "200",
        description = "Success response"
    )
    @PostMapping("/credit")
    public BaseResponse creditAccount(@RequestBody CreditDebitRequest request) {        
        return service.creditAccount(request);
    }

    @Operation(
        summary = "Debit account",
        description = "Endpoint to debit account"
    )
    @ApiResponse(
        responseCode = "200",
        description = "Success response"
    )
    @PostMapping("/debit")
    public BaseResponse debitAccount(@RequestBody CreditDebitRequest request) {        
        return service.debitAccount(request);
    }

    @Operation(
        summary = "Transfer fund",
        description = "Endpoint to tranfer fund"
    )
    @ApiResponse(
        responseCode = "200",
        description = "Success response"
    )
    @PostMapping("/transfer")
    public BaseResponse transfer(@RequestBody TransferData data){
        return transferService.transfer(data);
    }
    
}
