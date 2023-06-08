package com.example.capstone_be.model;

import com.example.capstone_be.util.enums.MessageType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Message  {

    @Enumerated(EnumType.STRING)
    private MessageType messageType;

    private String message;
    private String room;

    private String username;


    public Message(MessageType type, String message) {
        this.messageType = type;
        this.message= message;
    }
}