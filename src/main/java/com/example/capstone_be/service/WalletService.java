package com.example.capstone_be.service;

import com.example.capstone_be.dto.wallet.WalletDto;
import org.springframework.stereotype.Service;

import java.util.UUID;


public interface WalletService {
    void createWallet(WalletDto walletDto, UUID userId);

    WalletDto getWalletByOrderId(UUID orderId);


}
