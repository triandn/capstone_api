package com.example.capstone_be.model;

import com.example.capstone_be.util.enums.OrderStatusEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.joda.time.DateTime;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
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
    private Timestamp orderDate;

    @Column(name="status_order", nullable = false)
    private String statusOrder;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name="order_id_block_chain")
    private String orderIdBlockChain;

    @Column(name="public_key")
    private String publicKey;

    @Column(name = "time_id",nullable = false)
    private UUID timeId;

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @ManyToOne
    @JoinColumn(name = "time_id",insertable = false,updatable = false)
    @JsonIgnore
    private TimeBookDetail timeBookDetail;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false,insertable = false,updatable = false)
    private User user;
}
