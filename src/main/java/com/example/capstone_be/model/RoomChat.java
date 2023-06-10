package com.example.capstone_be.model;

import com.example.capstone_be.util.enums.MessageType;
import lombok.*;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

@Entity(name = "room_chats")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class RoomChat extends  BaseEntity {
    @Id
    @Column(name = "room_id", nullable = false)
    private UUID roomId = UUID.randomUUID();

    @Column(name = "room_name")
    private String roomName;

    @OneToMany(mappedBy = "roomChat")
    Set<Participant> participants;
}