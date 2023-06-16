package com.example.capstone_be.service;

import com.example.capstone_be.dto.wallet.WalletDto;
import com.example.capstone_be.model.Tour;
import com.example.capstone_be.model.Wallet;
import com.example.capstone_be.repository.WalletRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import javax.transaction.Transactional;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.Optional;
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
        wallet.setBankName(walletDto.getBankName());
        walletRepository.save(wallet);
    }

    @Override
    public WalletDto getWalletByOrderId(UUID orderId) {
        WalletDto walletDto = new WalletDto();
        Wallet wallet = walletRepository.getWalletByOrderId(orderId);
        walletDto.setWalletId(wallet.getWalletId());
        walletDto.setAccountNumber(wallet.getAccountNumber());
        walletDto.setTotalMoney(wallet.getTotalMoney());
        walletDto.setBankName(wallet.getBankName());
        return walletDto;
    }

    @Override
    public WalletDto getWalletByUserId(UUID userId) {
        Wallet wallet = walletRepository.getWalletByUserId(userId);
        WalletDto walletDto = new WalletDto();
        walletDto.setWalletId(wallet.getWalletId());
        walletDto.setTotalMoney(wallet.getTotalMoney());
        walletDto.setAccountNumber(wallet.getAccountNumber());
        walletDto.setBankName(wallet.getBankName());
        return  walletDto;
    }

    @Override
    public void updateWalletByField(UUID id,Map<String, Object> fields) {
        try{
            Optional<Wallet> existingWallet = walletRepository.findById(id);
            if(existingWallet.isPresent()){
                fields.forEach((key,value)->{
                    Field field = ReflectionUtils.findField(Wallet.class,key);
                    field.setAccessible(true);
                    ReflectionUtils.setField(field,existingWallet.get(),value);
                });
                walletRepository.save(existingWallet.get());
            }
        }
        catch (Exception e)
        {
            System.out.println("Error"+ e);
        }
    }
}
