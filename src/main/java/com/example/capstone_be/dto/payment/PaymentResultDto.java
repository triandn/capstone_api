package com.example.capstone_be.dto.payment;

import com.example.capstone_be.model.Order;
import com.example.capstone_be.model.Payment;
import lombok.Data;

import java.io.Serializable;

@Data
public class PaymentResultDto implements Serializable {
    private Payment payment;
    private Order order;
}
