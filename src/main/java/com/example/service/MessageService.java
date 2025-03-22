package com.example.service;

import com.example.entity.Message;
import com.example.entity.Account;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private AccountRepository accountRepository;
    
    

    // Create a new message
    public Message createMessage(Message message) {
        if (message.getMessageText().isBlank() || message.getMessageText().length() > 255) {
            throw new IllegalArgumentException("Message text cannot be blank or exceed 255 characters.");
        }
        if (message.getPostedBy() == null || !accountRepository.existsById(message.getPostedBy())) {
            throw new IllegalArgumentException("User with id " + message.getPostedBy() + " does not exist.");
        }
        return messageRepository.save(message);
    }
    

    // Get all messages
    public List<Message> getAllMessages() {
        return messageRepository.findAll();  // Return all messages
    }

    // Get a specific message by its ID
    public Optional<Message> getMessageById(int messageId) {
        return messageRepository.findById(messageId);  // Return message by ID
    }

    // Delete a message by ID
    // Delete a message by ID
    public boolean deleteMessage(int messageId) {
        Optional<Message> message = messageRepository.findById(messageId);
        if (message.isPresent()) {
            messageRepository.deleteById(messageId);
            return true; // 204 No Content will be returned automatically if nothing is returned.
        }
        return false; // Return false if message doesn't exist
    }
    


public Message updateMessage(int messageId, String newMessageText) {
    if (newMessageText == null || newMessageText.trim().isEmpty()) {
        throw new IllegalArgumentException("Message text cannot be empty.");
    }
    if (newMessageText.length() > 255) {
        throw new IllegalArgumentException("Message text cannot exceed 255 characters.");
    }

    Optional<Message> messageOpt = messageRepository.findById(messageId);
    if (!messageOpt.isPresent()) {
        throw new IllegalArgumentException("Message with id " + messageId + " not found.");
    }

    Message existingMessage = messageOpt.get();
    existingMessage.setMessageText(newMessageText);
    return messageRepository.save(existingMessage);
}

    
    

    // Get messages by a specific user
    //public List<Message> getMessagesByUser(int accountId) {
    //    return messageRepository.findByPostedBy(accountId);  // Retrieve messages by user
    //}
    //Retrieves message for a given accountId
    public List<Message> getMessagesByAccountId(int accountId) {
        return messageRepository.findByPostedBy(accountId); // Retrieve messages using the repository
    }
    

}



