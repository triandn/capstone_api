package com.example.capstone_be.service;

import com.example.capstone_be.dto.wallet.WalletDto;
import com.example.capstone_be.model.Wallet;
import com.example.capstone_be.repository.WalletRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.UUID;

@Service
public class WalletServiceImpl implements WalletService{
    private final WalletRepository walletRepository;

    public WalletServiceImpl(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    @Transactional
    @Override
    public void createWallet(WalletDto walletDto, UUID userId) {
        Wallet wallet = new Wallet();
//        wallet.setWalletId(walletDto.getWalletId());
        wallet.setAccountNumber(walletDto.getAccountNumber());
        wallet.setTotalMoney(walletDto.getTotalMoney());
        wallet.setUserId(userId);
        walletRepository.save(wallet);
    }

    @Override
    public WalletDto getWalletByOrderId(UUID orderId) {
        WalletDto walletDto = new WalletDto();
        Wallet wallet = walletRepository.getWalletByOrderId(orderId);
        walletDto.setWalletId(wallet.getWalletId());
        walletDto.setAccountNumber(wallet.getAccountNumber());
        walletDto.setTotalMoney(wallet.getTotalMoney());
        return walletDto;
    }
}
