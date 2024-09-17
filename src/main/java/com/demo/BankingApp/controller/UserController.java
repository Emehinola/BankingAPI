package com.demo.BankingApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.BankingApp.dto.ApiResponse;
import com.demo.BankingApp.dto.CreditDebitRequest;
import com.demo.BankingApp.dto.UserRequest;
import com.demo.BankingApp.service.impl.UserService;

@RestController
@RequestMapping("/api/user")
public class UserController {
    
    @Autowired
    UserService service;

    @PostMapping("/")
    public ApiResponse createAccount(@RequestBody UserRequest request){
        return service.createAccount(request);
    }

    @DeleteMapping("/{userId}")
    public ApiResponse deleteAccount(@PathVariable Long userId){
        return service.deleteAccount(userId);
    }

    @GetMapping("/balance/{accountNumber}")
    public ApiResponse getBalance(@PathVariable String accountNumber){
        return service.balanceEnquiry(accountNumber);
    }

    @PostMapping("/credit")
    public ApiResponse creditAccount(@RequestBody CreditDebitRequest request) {        
        return service.creditAccount(request);
    }

    @PostMapping("/debit")
    public ApiResponse debitAccount(@RequestBody CreditDebitRequest request) {        
        return service.debitAccount(request);
    }
    
}
