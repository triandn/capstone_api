package com.example.capstone_be.dto.guest;

import com.example.capstone_be.model.TimeBookDetail;
import com.example.capstone_be.model.Tour;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GuestDto {

    private UUID guestId = UUID.randomUUID();

    private String guestType;

    private int quantity;

    private UUID timeId;

    private UUID userId;
}
