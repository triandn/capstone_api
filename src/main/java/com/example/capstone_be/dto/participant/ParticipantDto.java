package com.example.capstone_be.dto.participant;

import lombok.Data;

import javax.persistence.Column;
import java.util.UUID;

@Data
public class ParticipantDto {
    private UUID Id = UUID.randomUUID();
    UUID user_id;
    UUID room_id;
}
