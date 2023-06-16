package com.example.capstone_be.dto.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {
    private UUID orderId = UUID.randomUUID();

    private Date orderDate;

    private String statusOrder;

    private BigDecimal price;

    private String tour_title;

    private String city;

    private String imageMain;

    private Double priceOnePerson;

    private UUID timeId;

    private UUID userId;
}
