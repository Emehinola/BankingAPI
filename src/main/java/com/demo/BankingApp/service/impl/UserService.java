package com.demo.BankingApp.service.impl;

import com.demo.BankingApp.dto.CreditDebitRequest;
import com.demo.BankingApp.dto.ApiResponse;
import com.demo.BankingApp.dto.UserRequest;

public interface UserService {

    ApiResponse createAccount(UserRequest request);
    ApiResponse deleteAccount(Long userId);
    ApiResponse balanceEnquiry(String accountNumber);
    ApiResponse creditAccount(CreditDebitRequest request);
    ApiResponse debitAccount(CreditDebitRequest request);

}
