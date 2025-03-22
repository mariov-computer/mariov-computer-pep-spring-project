package com.example.service;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    // Method to register a new user
    public Account register(Account account) {
        // Validate username and password
        if (account.getUsername().isBlank() || account.getPassword().length() < 4) {
            throw new IllegalArgumentException("Invalid input data: Username cannot be blank and password must be at least 4 characters.");
        }

        // Check if username already exists
        if (accountRepository.existsByUsername(account.getUsername())) {
            throw new IllegalArgumentException("Username already exists");
        }

        // Save the new account to the database
        return accountRepository.save(account);
    }

     // Method to log in a user, now accepting username and password as separate Strings
     public Account login(String username, String password) {
        // Find the account by username
        Account existingAccount = accountRepository.findByUsername(username);

        // If the account doesn't exist, throw an exception
        if (existingAccount == null) {
            throw new IllegalArgumentException("Account not found");
        }

        // If the password doesn't match, throw an exception
        if (!existingAccount.getPassword().equals(password)) {
            throw new IllegalArgumentException("Invalid password");
        }

        // Return the account if login is successful
        return existingAccount;
    }
    public boolean usernameExists(String username) {
        return accountRepository.existsByUsername(username);
    }

    
}