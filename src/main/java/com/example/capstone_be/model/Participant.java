package com.example.capstone_be.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

@Entity(name="participants")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Participant extends BaseEntity{
    @Id
    @Column(name = "id", nullable = false)
    private UUID Id = UUID.randomUUID();

    @Column(name = "user_id", nullable = false,insertable = false,updatable = false)
    UUID user_id;

    @Column(name = "room_id", nullable = false,insertable = false,updatable = false)
    UUID room_id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;

    @ManyToOne
    @JoinColumn(name = "room_id")
    RoomChat roomChat;
}
