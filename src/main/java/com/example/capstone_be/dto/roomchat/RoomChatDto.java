package com.example.capstone_be.dto.roomchat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomChatDto implements Serializable {

    private UUID roomId = UUID.randomUUID();

    private String roomName;
}