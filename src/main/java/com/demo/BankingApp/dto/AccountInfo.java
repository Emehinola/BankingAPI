package com.demo.BankingApp.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.*;

import io.swagger.v3.oas.annotations.media.Schema;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountInfo {

    @Schema(name = "Account name")
    private String accountName;
    @Schema(name = "Account number")
    private String accountNumber;
    @Schema(name = "Account balance")
    private BigDecimal accountBalance;

}
