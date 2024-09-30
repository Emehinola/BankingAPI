package com.demo.BankingApp.service;

import com.demo.BankingApp.dto.CreditDebitRequest;
import com.demo.BankingApp.dto.BaseResponse;
import com.demo.BankingApp.dto.UserRequest;
import com.demo.BankingApp.model.User;

public interface UserService {

    BaseResponse createAccount(UserRequest request);
    BaseResponse deleteAccount(Long userId);
    BaseResponse balanceEnquiry(String accountNumber);
    BaseResponse creditAccount(CreditDebitRequest request);
    BaseResponse debitAccount(CreditDebitRequest request);
    boolean accountExists(String accountNumber);
    User getUserByAccountNumber(String accountNumber);

}
