package com.example.capstone_be.dto.message;

import com.example.capstone_be.util.enums.MessageType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.io.Serializable;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageDto implements Serializable {

    private UUID messageId = UUID.randomUUID();

    private String messageType;

    private String message;

    private String username;
}
