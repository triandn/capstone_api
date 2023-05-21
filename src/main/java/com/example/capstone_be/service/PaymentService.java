package com.example.capstone_be.service;

import com.example.capstone_be.dto.guest.GuestDto;
import com.example.capstone_be.dto.payment.PaymentGuestItemDto;
import com.example.capstone_be.model.Payment;
import com.example.capstone_be.util.common.ResponseDataAPI;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.UUID;

public interface PaymentService {
    ResponseDataAPI makePayment(UUID userId, String language, UUID timeId,
                                List<GuestDto> guestDtos, Long tourId)
            throws UnsupportedEncodingException;

    Payment findByTxnRef(String txnRef);

    ResponseDataAPI paymentResult(String responseCode, String txnRef);
}