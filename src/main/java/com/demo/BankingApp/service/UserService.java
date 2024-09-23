package com.demo.BankingApp.service;

import com.demo.BankingApp.dto.CreditDebitRequest;
import com.demo.BankingApp.dto.ApiResponse;
import com.demo.BankingApp.dto.UserRequest;
import com.demo.BankingApp.model.User;

public interface UserService {

    ApiResponse createAccount(UserRequest request);
    ApiResponse deleteAccount(Long userId);
    ApiResponse balanceEnquiry(String accountNumber);
    ApiResponse creditAccount(CreditDebitRequest request);
    ApiResponse debitAccount(CreditDebitRequest request);
    boolean accountExists(String accountNumber);
    User getUserByAccountNumber(String accountNumber);

}
