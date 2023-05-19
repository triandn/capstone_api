package com.example.capstone_be.service;


import com.example.capstone_be.exception.NotFoundException;
import com.example.capstone_be.model.Payment;
import com.example.capstone_be.model.TimeBookDetail;
import com.example.capstone_be.repository.PaymentRepository;
import com.example.capstone_be.repository.TimeBookRepository;
import com.example.capstone_be.util.common.Common;
import com.example.capstone_be.util.common.ResponseDataAPI;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.security.SecureRandom;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;
    private final UserService userService;
    private final TimeBookDetailService timeBookDetailService;
    private final TimeBookRepository timeBookRepository;
    @Override
    public ResponseDataAPI makePayment(UUID userId, String language, UUID timeId) throws UnsupportedEncodingException {
        TimeBookDetail timeBook = timeBookDetailService.findTimeBookById(timeId);
        if (timeBook.getIsPayment()) {
            throw new NotFoundException("PAYMENT NOT FOUND");
        }
        String returnUrl = "https://experience-travel.vercel.app/";

        String vnp_Version = "2.1.0";
        String vnp_Command = "pay";

        String vnp_TxnRef = String.format("%08d", new SecureRandom().nextInt(10_000_000));
        String vnp_IpAddr = "127.0.0.1";
        String vnp_TmnCode = Common.VNP_CODE;
        String vnp_OrderInfo = "PAYMENT TOUR";
        String orderType = "other";
        String locate = language;

//        BigDecimal amount = timeBook.getPrice().multiply(new BigDecimal("100"));
        return null;
    }

    @Override
    public Payment findByTxnRef(String txnRef) {
        return null;
    }

    @Override
    public ResponseDataAPI paymentResult(String responseCode, String txnRef) {
        return null;
    }
}
