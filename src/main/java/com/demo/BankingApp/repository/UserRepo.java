package com.demo.BankingApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.demo.BankingApp.model.User;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {

    Boolean existsByEmail(String email);
    Boolean existsByAccountNumber(String accountNumber);
    User findByAccountNumber(String accountNumber);

}
