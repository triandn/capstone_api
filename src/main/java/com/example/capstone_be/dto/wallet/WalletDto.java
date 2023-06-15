package com.example.capstone_be.dto.wallet;

import com.example.capstone_be.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WalletDto implements Serializable {

    private UUID walletId = UUID.randomUUID();

    private String accountNumber;

    private BigDecimal totalMoney;
}
