package com.demo.BankingApp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmailDetails {

    private String receipient;
    private String subject;
    private String message;
    private String attachment;
}
