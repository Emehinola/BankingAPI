package com.demo.BankingApp.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountInfo {

    private String accountName;
    private String accountNumber;
    private BigDecimal accountBalance;

}
