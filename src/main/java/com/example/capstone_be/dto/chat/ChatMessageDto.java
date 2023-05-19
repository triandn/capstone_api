package com.example.capstone_be.dto.chat;

import com.example.capstone_be.model.BaseEntity;
import com.example.capstone_be.model.User;
import com.example.capstone_be.util.enums.MessageType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ChatMessageDto {

    private String senderName;

    private String receiverName;

    private String message;

    private LocalDateTime time_stamp = LocalDateTime.now();

    private MessageType status;

}