package com.demo.BankingApp.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransferData {
    
    private BigDecimal amount;
    private String senderAccountNumber;
    private String receiverAccountNumber;
    private String statement;
}
