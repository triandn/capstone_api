package com.example.capstone_be.model;

import com.example.capstone_be.util.enums.OrderStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name="orders")
public class Order extends BaseEntity {
    @Id
    @Column(name = "order_id", nullable = false)
    private UUID orderId = UUID.randomUUID();
    @Column(name="order_date")
    private Date orderDate;
    @Column(name="status_order", nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatusEnum statusOrder;
}
