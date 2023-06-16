package com.example.capstone_be.repository;

import com.example.capstone_be.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.UUID;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, UUID> {
    String GET_WALLET="SELECT * FROM wallets AS w INNER JOIN users AS u ON u.user_id = w.user_id\n" +
            "INNER JOIN orders AS o ON o.user_id = u.user_id WHERE o.order_id=:order_id";
    @Query(value = GET_WALLET,nativeQuery = true)
    Wallet getWalletByOrderId(UUID order_id);

    @Modifying
    @Query(value = "UPDATE wallets SET total_money=:total_money WHERE wallet_id=:wallet_id",nativeQuery = true)
    void updateTotalMoney(UUID wallet_id, BigDecimal total_money);

    @Query(value = "SELECT * FROM wallets WHERE user_id=:user_id",nativeQuery = true)
    Wallet getWalletByUserId(UUID user_id);
}
