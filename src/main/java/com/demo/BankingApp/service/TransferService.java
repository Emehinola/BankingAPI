package com.demo.BankingApp.service;

import com.demo.BankingApp.dto.BaseResponse;
import com.demo.BankingApp.dto.TransferData;

public interface TransferService {

    public BaseResponse transfer(TransferData data);
}
