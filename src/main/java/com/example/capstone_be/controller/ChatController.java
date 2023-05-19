package com.example.capstone_be.controller;

import com.example.capstone_be.dto.chat.ChatMessageDto;
import com.example.capstone_be.model.ChatMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ChatController {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/message")
    @SendTo("/chatroom/public")
    public ChatMessageDto receiveMessage(@Payload ChatMessageDto message){
        System.out.println("Message Room: " + message);
        return message;
    }

    @MessageMapping("/private-message")
    public ChatMessageDto recMessage(@Payload ChatMessageDto message){
        simpMessagingTemplate.convertAndSendToUser(message.getReceiverName(),"/private",message);
        System.out.println("Message: "+message);
        return message;
    }

}