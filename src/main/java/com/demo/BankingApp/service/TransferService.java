package com.demo.BankingApp.service;

import com.demo.BankingApp.dto.ApiResponse;
import com.demo.BankingApp.dto.TransferData;

public interface TransferService {

    public ApiResponse transfer(TransferData data);
}
