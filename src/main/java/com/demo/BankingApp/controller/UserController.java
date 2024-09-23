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
import com.demo.BankingApp.dto.TransferData;
import com.demo.BankingApp.dto.UserRequest;
import com.demo.BankingApp.service.TransferService;
import com.demo.BankingApp.service.UserService;

// import io.swagger.v3.oas.annotations.Operation;
// import io.swagger.v3.oas.annotations.responses.ApiResponse;
// import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/user")
// @Tag(name = "User account management API")
public class UserController {
    
    @Autowired
    UserService service;

    @Autowired
    TransferService transferService;

    // @Operation(
    //     summary = "Account creation",
    //     description = "This is account creation endpoint"
    // )
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

    @PostMapping("/transfer")
    public ApiResponse transfer(@RequestBody TransferData data){
        return transferService.transfer(data);
    }
    
}
