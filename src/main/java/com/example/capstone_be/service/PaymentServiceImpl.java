package com.example.capstone_be.service;


import com.example.capstone_be.dto.guest.GuestDto;
import com.example.capstone_be.dto.payment.PaymentGuestItemDto;
import com.example.capstone_be.dto.payment.PaymentResultDto;
import com.example.capstone_be.exception.NotFoundException;
import com.example.capstone_be.model.Order;
import com.example.capstone_be.model.Payment;
import com.example.capstone_be.model.TimeBookDetail;
import com.example.capstone_be.model.Wallet;
import com.example.capstone_be.repository.*;
import com.example.capstone_be.util.common.Common;
import com.example.capstone_be.util.common.CommonFunction;
import com.example.capstone_be.util.common.ResponseDataAPI;
import com.example.capstone_be.util.enums.GuestType;
import com.example.capstone_be.util.enums.OrderStatusEnum;
import com.example.capstone_be.util.enums.PaymentStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;
    private final UserService userService;
    private final TimeBookDetailService timeBookDetailService;
    private final TimeBookRepository timeBookRepository;
    private final GuestRepository guestRepository;
    private final TourRepository tourRepository;
    private final GuestService guestService;
    private final OrderService orderService;
    private final OrderRepository orderRepository;
    private final WalletRepository walletRepository;
    private final WalletService walletService;
    @Override
    public ResponseDataAPI makePayment(UUID userId, String language, UUID timeId, List<GuestDto> guestDtos, Long tourId,Double priceTotal) throws UnsupportedEncodingException {
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

        BigDecimal amount = BigDecimal.valueOf(priceTotal);
        Map<String,Integer> map = new HashMap<>();
        for (GuestDto item: guestDtos) {
            map.put(String.valueOf(item.getGuestType()),item.getQuantity());
        }
        // ADD GUEST LIST
        List<GuestDto> guestDtoList = new ArrayList<>();
        GuestDto guestDto = null;
        for (GuestDto item : guestDtos) {
            guestDto = new GuestDto();
            guestDto.setGuestId(UUID.randomUUID());
            guestDto.setGuestType(item.getGuestType());
            guestDto.setQuantity(item.getQuantity());
            guestDto.setTimeId(timeId);
            guestDto.setUserId(userId);
            guestDtoList.add(guestDto);
        }

        System.out.println("Guest List: " + guestDtoList.size());
        guestService.createGuests(guestDtoList); // save

        // VNPAY PROPERTIES

        Order order = new Order();
        order.setOrderDate(new Date());
        order.setStatusOrder(OrderStatusEnum.WAITING.toString());
        order.setTimeId(timeId);
        order.setUserId(userId);
        order.setPrice(BigDecimal.valueOf(priceTotal));

        Map vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", vnp_Version);
        vnp_Params.put("vnp_Command", vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(amount.longValue()));
        vnp_Params.put("vnp_CurrCode", "VND");
        vnp_Params.put("vnp_TxnRef", order.getOrderId());
        vnp_Params.put("vnp_OrderInfo", vnp_OrderInfo);
        vnp_Params.put("vnp_OrderType", orderType);
        vnp_Params.put("vnp_Locale", locate);
        vnp_Params.put("vnp_ReturnUrl", returnUrl);
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);

        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(calendar.getTime());
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

        calendar.add(Calendar.MINUTE, 30);
        String vnp_ExpireDate = formatter.format(calendar.getTime());
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);
        List fieldNames = new ArrayList(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator iterator = fieldNames.iterator();

        while (iterator.hasNext()) {
            String fieldName = (String) iterator.next();
            String fieldValue = (String) vnp_Params.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                hashData.append(fieldName);
                hashData.append('=');
                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
                query.append('=');
                query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                if (iterator.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }

        String queryUrl = query.toString();
        String vnp_SecureHash = CommonFunction.hmacSha512(Common.VNP_HASH_SECRET, hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
        String paymentUrl = Common.VNP_URL + "?" + queryUrl;
        //Save Order


        orderRepository.save(order);
        //Save Payment
        Wallet wallet = walletRepository.getWalletByOrderId(order.getOrderId());
        wallet.setTotalMoney(wallet.getTotalMoney().subtract(BigDecimal.valueOf(priceTotal)));
        Payment payment = new Payment();
        payment.setCreatedAt(Common.getCurrentDateTime().toLocalDateTime());
        payment.setVnpOrderInfo(vnp_OrderInfo);
        payment.setOrderType(orderType);
        payment.setAmount(amount.divide(new BigDecimal("100")));
        payment.setLocate(locate);
        payment.setIpAddress(vnp_IpAddr);
        payment.setPaymentUrl(paymentUrl);
        payment.setStatus(PaymentStatus.SUCCESS);
        payment.setTxnRef(order.getOrderId().toString());
        payment.setTimeOver(calendar.getTime());
        payment.setUserId(userId);
        payment.setUser(userService.getUserByUserId(userId));
        payment.setTimeId(timeId);
        payment.setTimeBookDetail(timeBook);
        paymentRepository.save(payment);

        PaymentResultDto paymentResultDto = new PaymentResultDto();
        paymentResultDto.setPayment(payment);
        paymentResultDto.setOrder(order);

        return ResponseDataAPI.successWithoutMeta(paymentResultDto);

    }

    @Override
    public Payment findByTxnRef(String txnRef) {
        return paymentRepository
                .findByTxnRef(txnRef)
                .orElseThrow(() -> new NotFoundException("PAYMENT NOT FOUND"));
    }

    @Override
    public ResponseDataAPI paymentResult(String responseCode, String txnRef) {
        Payment payment = this.findByTxnRef(txnRef);
        if (responseCode.equals("00")) {
            payment.setStatus(PaymentStatus.SUCCESS);
            payment.setUpdatedAt(Common.getCurrentDateTime().toLocalDateTime());
            TimeBookDetail timeBookDetail = timeBookDetailService.findTimeBookById(payment.getTimeBookDetail().getTimeId());
            timeBookDetail.setIsPayment(true);
            timeBookRepository.save(timeBookDetail);
        } else {
            payment.setStatus(PaymentStatus.FAILURE);
            payment.setUpdatedAt(Common.getCurrentDateTime().toLocalDateTime());
        }
        Payment result = paymentRepository.save(payment);
        return ResponseDataAPI.successWithoutMeta(result);
    }
}
