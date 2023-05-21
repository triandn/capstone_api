package com.example.capstone_be.model;

import com.example.capstone_be.util.enums.PaymentStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "payments")
public class Payment extends BaseEntity{
    @Id
    @Column(name = "payment_id", nullable = false)
    private UUID payment_id = UUID.randomUUID();

    @Column(name = "vnp_order_info")
    private String vnpOrderInfo;

    @Column(name = "order_type")
    private String orderType;

    @Column(name = "amount",nullable = false)
    private BigDecimal amount;

    @Column(name = "locate",nullable = false)
    private String locate;

    @Column(name = "ip_address",nullable = false)
    private String ipAddress;

    @Column(name = "payment_url")
    private String paymentUrl;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    @Column(name = "tnx_ref",nullable = false)
    private String txnRef;

    @Column(name = "time_over",nullable = false)
    private Date timeOver;

    @Column(name = "time_id",nullable = false,insertable = false,updatable = false)
    private UUID timeId;

    @Column(name = "user_id", nullable = false,insertable = false,updatable = false)
    private UUID userId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    @ManyToOne
    @JoinColumn(name = "time_id")
    @JsonIgnore
    private TimeBookDetail timeBookDetail;
}
