package com.example.capstone_be.dto.payment;

import com.example.capstone_be.util.enums.GuestType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentGuestItemDto implements Serializable {
    private GuestType guestType;
    private int quantity;
}
