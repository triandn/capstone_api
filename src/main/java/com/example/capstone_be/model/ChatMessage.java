package com.example.capstone_be.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name="chat_messages")
public class ChatMessage extends BaseEntity {

    @Id
    @Column(name = "chat_id", nullable = false)
    private UUID chatId = UUID.randomUUID();

    @Column(name = "sender_name")
    private String senderName;

    @Column(name = "receiver_name")
    private String receiverName;

    @Column(name = "message")
    private String message;

    @Column(name = "time_stamp")
    private LocalDateTime time_stamp = LocalDateTime.now();

    @Column(name = "type")
    private String type;

    @Column(name = "user_id", nullable = false,insertable = false,updatable = false)
    private UUID userId;

    @ManyToOne(targetEntity = User.class, fetch = FetchType.EAGER,cascade=CascadeType.ALL)
    @JoinColumn(nullable = false, name = "user_id")
    private User user;
}
