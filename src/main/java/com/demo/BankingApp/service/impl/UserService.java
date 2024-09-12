package com.demo.BankingApp.service.impl;

import com.demo.BankingApp.dto.ApiResponse;
import com.demo.BankingApp.dto.UserRequest;

public interface UserService {

    ApiResponse createAccount(UserRequest request);

}
