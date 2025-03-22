package com.example.controller;

import com.example.entity.Account;
import com.example.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SocialMediaController {

    @Autowired
    private AccountService accountService;

    // Register endpoint
    @PostMapping("/register")
public ResponseEntity<Account> register(@RequestBody Account account) {
    try {
        // Check if username already exists
        if (accountService.usernameExists(account.getUsername())) {
            // Return 409 Conflict if the username is already taken
            return ResponseEntity.status(409).build();
        }
        
        Account createdAccount = accountService.register(account);
        return ResponseEntity.ok(createdAccount); // Return 200 OK with the created account
    } catch (IllegalArgumentException e) {
        return ResponseEntity.status(400).build(); // Bad request
    } catch (Exception e) {
        return ResponseEntity.status(500).build(); // Internal server error for other issues
    }
}

    

    // Login endpoint
    @PostMapping("/login")
    public ResponseEntity<Account> login(@RequestBody Account account) {
        try {
            // Login logic
            Account loggedInAccount = accountService.login(account.getUsername(), account.getPassword());
            return ResponseEntity.ok(loggedInAccount); // OK response with account data
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(401).build(); // Unauthorized
        } catch (Exception e) {
            return ResponseEntity.status(400).build(); // Bad request
        }
    }
    

    
    
}
