package com.example.controller;

import com.example.entity.Message;
import com.example.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class MessageController {

    @Autowired
    private MessageService messageService;

    // Create a new message
    @PostMapping("/messages")
    public ResponseEntity<Message> createMessage(@RequestBody Message message) {
        try {
            Message createdMessage = messageService.createMessage(message);
            return ResponseEntity.ok(createdMessage); // OK response with the created message
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(400).build(); // Bad request due to validation failure
        } catch (Exception e) {
            return ResponseEntity.status(500).build(); // Internal server error
        }
    }

    // Get all messages
    @GetMapping("/messages")
    public ResponseEntity<List<Message>> getAllMessages() {
        try {
            List<Message> messages = messageService.getAllMessages();
            return ResponseEntity.ok(messages); // OK response with list of messages
        } catch (Exception e) {
            return ResponseEntity.status(500).build(); // Internal server error
        }
    }

    // Get a message by ID
    @GetMapping("/messages/{messageId}")
    public ResponseEntity<Message> getMessageById(@PathVariable int messageId) {
        try {
            Optional<Message> optionalMessage = messageService.getMessageById(messageId);
            return optionalMessage.map(ResponseEntity::ok)
                                  .orElseGet(() -> ResponseEntity.ok().build());  // Return 200 OK with empty body if not found
        } catch (Exception e) {
            return ResponseEntity.status(500).build();  // Return 500 for any internal server errors
        }
    }

    // Delete a message by ID
    @DeleteMapping("/messages/{messageId}")
public ResponseEntity<Void> deleteMessage(@PathVariable int messageId) {
    boolean deleted = messageService.deleteMessage(messageId);
    if (deleted) {
        return ResponseEntity.ok().build();  // Return 200 OK if message deleted
    } else {
        // Return 200 OK even if message is not found, based on your test's expectation
        return ResponseEntity.ok().build();  // Returning 200 OK for "not found" scenario
    }
}


    




    // Update a message by ID
    @PatchMapping("/messages/{messageId}")
public ResponseEntity<Message> updateMessage(@PathVariable int messageId, @RequestBody Message updatedMessage) {
    try {
        Message updated = messageService.updateMessage(messageId, updatedMessage.getMessageText());
        return ResponseEntity.ok(updated); // Return the updated message in response
    } catch (IllegalArgumentException e) {
        return ResponseEntity.status(400).build(); // Bad Request if validation fails
    } catch (Exception e) {
        return ResponseEntity.status(500).build(); // Internal Server Error
    }
}


@GetMapping("/accounts/{accountId}/messages")
public ResponseEntity<List<Message>> getMessagesByAccountId(@PathVariable int accountId) {
    try {
        List<Message> messages = messageService.getMessagesByAccountId(accountId);
        return ResponseEntity.ok(messages); // Return 200 with the messages
    } catch (Exception e) {
        return ResponseEntity.status(500).build(); // Internal Server Error
    }
    }
}
