package com.example.capstone_be.controller;

import com.example.capstone_be.dto.tour.TourCreateDto;
import com.example.capstone_be.dto.wallet.WalletDto;
import com.example.capstone_be.model.Tour;
import com.example.capstone_be.model.User;
import com.example.capstone_be.model.Wallet;
import com.example.capstone_be.repository.UserRepository;
import com.example.capstone_be.response.TourResponseByOwner;
import com.example.capstone_be.service.UserService;
import com.example.capstone_be.service.WalletService;
import com.example.capstone_be.util.common.CommonFunction;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/wallet")
public class WalletController {
    @Autowired
    private UserService userService;
    @Autowired
    private WalletService walletService;

    private final UserRepository userRepository;

    public WalletController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping(path = "/create/")
    public ResponseEntity<?> createWallet(@Valid @RequestBody WalletDto walletDto, HttpServletRequest request) {
        String bearerToken = CommonFunction.getBearToken(request);
        Claims claims = CommonFunction.getClaims(bearerToken);
        String email = claims.getSubject();
        User user = userRepository.getUserByUserEmail(email);
        user.setIsWallet(true);
        userRepository.save(user);
        walletService.createWallet(walletDto,user.getUserId());
        return new ResponseEntity<>("Create Success", HttpStatus.CREATED);
    }

    @GetMapping("/get-wallet/")
    public ResponseEntity<?> getWallet(HttpServletRequest request) {
        String bearerToken = CommonFunction.getBearToken(request);
        Claims claims = CommonFunction.getClaims(bearerToken);
        String email = claims.getSubject();
        User user = userRepository.getUserByUserEmail(email);
        WalletDto walletDto = walletService.getWalletByUserId(user.getUserId());
        return new ResponseEntity(walletDto, HttpStatus.OK);
    }

    @PatchMapping("/wallet-update/{id}")
    public ResponseEntity<?>  updateTour(@PathVariable String id, @RequestBody Map<String,Object> fields) {
        walletService.updateWalletByField(UUID.fromString(id),fields);
        return new ResponseEntity<>("UPDATE SUCCESS", HttpStatus.OK);
    }

}
