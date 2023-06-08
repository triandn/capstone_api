package com.example.capstone_be.controller;

import com.example.capstone_be.dto.guest.GuestDto;
import com.example.capstone_be.dto.payment.PaymentGuestItemDto;
import com.example.capstone_be.model.User;
import com.example.capstone_be.service.PaymentService;
import com.example.capstone_be.service.UserService;
import com.example.capstone_be.util.common.ResponseDataAPI;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/payment")
public class PaymentController {
    private final PaymentService paymentService;
    private final UserService userService;

    @Value("${jwt.secret}")
    private String secretKey;

    public PaymentController(PaymentService paymentService, UserService userService) {
        this.paymentService = paymentService;
        this.userService = userService;
    }

    @PostMapping("/{tour_id}/{time_book_id}/{price_total}")
    public ResponseEntity<ResponseDataAPI> payment(
            HttpServletRequest request,
            @RequestParam(value = "language", defaultValue = "vn") String language,
            @PathVariable UUID time_book_id,@RequestBody List<GuestDto> guestDtos,
            @PathVariable Long tour_id, @PathVariable String price_total)
            throws UnsupportedEncodingException {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            bearerToken = bearerToken.substring(7);
        }
        Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(bearerToken).getBody();
        String email = claims.getSubject();
        User user = userService.getUserByEmail(email);
        Double priceTotal = Double.valueOf(price_total);
        return ResponseEntity.ok(paymentService.makePayment(user.getUserId(), language,time_book_id,guestDtos,tour_id,priceTotal));
    }
    @GetMapping("/")
    public ResponseEntity<ResponseDataAPI> paymentResult(
            @RequestParam(value = "vnp_ResponseCode", defaultValue = "00") String responseCode,
            @RequestParam(value = "vnp_TxnRef", defaultValue = "") String txnRef) {
        return ResponseEntity.ok(paymentService.paymentResult(responseCode, txnRef));
    }
}
