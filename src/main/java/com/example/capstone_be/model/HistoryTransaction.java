package com.example.capstone_be.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name="history_transactions")
public class HistoryTransaction {
    @Id
    @Column(name = "transaction_id", nullable = false)
    private UUID transactionId = UUID.randomUUID();

    @Column(name="transaction_date")
    private Date transaction_date;

    @Column(name = "order_id", nullable = false)
    private UUID orderId;

    @Column(name = "money")
    private BigDecimal money;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false,insertable = false,updatable = false)
    private Order order;

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false,insertable = false,updatable = false)
    private User user;
}
