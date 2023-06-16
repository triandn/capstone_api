package com.example.capstone_be.service;

import com.example.capstone_be.dto.wallet.WalletDto;
import com.example.capstone_be.model.Wallet;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;


public interface WalletService {
    void createWallet(WalletDto walletDto, UUID userId);

    WalletDto getWalletByOrderId(UUID orderId);

    WalletDto getWalletByUserId(UUID userId);

    void updateWalletByField(UUID id,Map<String, Object> fields);
}
