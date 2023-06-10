package com.example.capstone_be.model;

import com.example.capstone_be.util.enums.MessageType;
import lombok.*;

import javax.persistence.*;
import java.util.UUID;

@Entity(name = "messages")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Message extends  BaseEntity {
    @Id
    @Column(name = "message_id", nullable = false)
    private UUID messageId = UUID.randomUUID();

    @Column(name = "message_type")
    private String messageType;

    @Column(name = "message")
    private String message;

    @Column(name = "user_id", nullable = false,insertable = false,updatable = false)
    UUID user_id;

    @Column(name = "room_id", nullable = false,insertable = false,updatable = false)
    UUID room_id;

    @Column(name = "username")
    private String username;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id", nullable = false)
    private RoomChat roomChat;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Message(String type, String message) {
        this.messageType = type;
        this.message= message;
    }
}