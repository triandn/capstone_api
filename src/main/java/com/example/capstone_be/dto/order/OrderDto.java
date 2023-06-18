package com.example.capstone_be.dto.order;

import com.example.capstone_be.dto.daybook.TimeBookViewDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;

import javax.persistence.Column;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {
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

    private UUID timeId;

    private UUID userId;

    private TimeBookViewDto timeBookViewDto;

    private Long tourId;
}
