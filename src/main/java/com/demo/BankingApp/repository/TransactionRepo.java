package com.demo.BankingApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.demo.BankingApp.model.Transaction;

@Repository
public interface TransactionRepo extends JpaRepository<Transaction, String> {}
