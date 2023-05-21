package com.example.capstone_be.util.common;

import java.sql.Timestamp;
import java.util.Date;

public class Common {
    public static final String VNP_CODE = "IE5GYO20";
    public static final String VNP_HASH_SECRET = "YDBASKPWKOUYOAEXUCHVEMWSOHUXFZOF";
    public static final String VNP_URL = "https://sandbox.vnpayment.vn/paymentv2/vpcpay.html";
    public static final String SUCCESS = "success";
    public static final String FAILURE = "failure";

    public static Timestamp getCurrentDateTime() {
        Date date = new Date();
        return new Timestamp(date.getTime());
    }
}
