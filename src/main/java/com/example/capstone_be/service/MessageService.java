package com.example.capstone_be.service;

import com.example.capstone_be.model.Message;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {

    public List<Message> getMessages(String room) {
//        return messageRepository.findAllByRoom(room);
        return null;
    }

    public Message saveMessage(Message message) {
//        return messageRepository.save(message);
        return message;
    }
}
