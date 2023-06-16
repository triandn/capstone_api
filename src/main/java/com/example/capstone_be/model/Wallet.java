package com.example.capstone_be.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name="wallets")
public class Wallet extends BaseEntity {
    @Id
    @Column(name = "wallet_id", nullable = false)
    private UUID walletId = UUID.randomUUID();

    @Column(name="account_number")
    private String accountNumber;

    @Column(name="total_money")
    private BigDecimal totalMoney;

    @Column(name = "bank_name")
    private String bankName;

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false,insertable = false,updatable = false)
    private User user;
}
