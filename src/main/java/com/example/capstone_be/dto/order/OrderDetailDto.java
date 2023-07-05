package com.example.capstone_be.dto.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailDto implements Serializable {
    private UUID orderId = UUID.randomUUID();

    private LocalDateTime orderDate;

    private String statusOrder;

    private BigDecimal price;

    private String tour_title;

    private String orderIdBlockChain;

    private String publicKey;

    private String city;

    private String imageMain;

    private Double priceOnePerson;

    private Date date_name;
}
