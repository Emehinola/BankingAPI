package com.demo.BankingApp.model;

import java.math.*;
import java.time.*;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder // builds object
@Entity
@Table(name = "Users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String otherName;
    private String gender;
    private String address;
    private String stateOfOrigin;
    private String accountNumber;
    private BigDecimal accountBalance;
    private String email;
    private String phoneNumber;
    private String altPhoneNumber;
    private String status;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime modifiedAt;

    public String getFullName(){
        StringBuilder builder = new StringBuilder();

        String name = builder.append(this.firstName).append(" ").append(this.lastName).toString();

        return name;
    }

    public Boolean hasSufficientFund(BigDecimal requestAmount){
        return this.accountBalance.compareTo(requestAmount) > 0;
    }

}
