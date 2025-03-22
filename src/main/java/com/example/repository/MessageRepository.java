package com.example.repository;

import com.example.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Integer> {

    // Custom query method to find messages by user (accountId)
    List<Message> findByPostedBy(int accountId);
}

