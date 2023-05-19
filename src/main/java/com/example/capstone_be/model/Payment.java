package com.example.capstone_be.model;

import com.example.capstone_be.util.enums.PaymentStatus;
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
    @GeneratedValue
    private UUID payment_id;

    private String vnpOrderInfo;

    private String orderType;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    private String locate;

    @Column(nullable = false)
    private String ipAddress;

    @Column(columnDefinition = "text")
    private String paymentUrl;

    @Column()
    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    @Column(nullable = false)
    private String txnRef;

    @Column(nullable = false)
    private Date timeOver;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "time_id")
    private TimeBookDetail timeBookDetail;
}
