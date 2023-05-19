package com.example.capstone_be.dto.payment;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PricePaymentDto implements Serializable {

    private Float price_one_person;

    private String guest_type;

    private int quantity;
}
