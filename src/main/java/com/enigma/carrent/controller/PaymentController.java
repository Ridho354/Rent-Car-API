package com.enigma.carrent.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.enigma.carrent.constant.Constant;
import com.enigma.carrent.dto.request.MidtransNotificationRequest;
import com.enigma.carrent.dto.response.PaymentResponse;
import com.enigma.carrent.service.PaymentService;
import com.enigma.carrent.util.ResponseUtil;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(Constant.API_PAYMENT)
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;

    @PostMapping("/checkout/{RentTransactionId}")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<?> checkout(@PathVariable("RentTransactionId") String id) {
        PaymentResponse createdPaymentResponse = paymentService.createPayment(id);
        return ResponseUtil.buildResponse(HttpStatus.OK, "Payment created", createdPaymentResponse);
    }

    @PostMapping("/notifications")
    public ResponseEntity<?> handleNotification(@RequestBody Map<String, Object> notificationRequest) {

        MidtransNotificationRequest midtransNotificationRequest = MidtransNotificationRequest.builder()
                .OrderId((String) notificationRequest.get("order_id"))
                .statusCode((String) notificationRequest.get("status_code"))
                .grossAmount((String) notificationRequest.get("gross_amount"))
                .signatureKey((String) notificationRequest.get("signature_key"))
                .transactionStatus((String) notificationRequest.get("transaction_status"))
                .transactionTime((String) notificationRequest.get("transaction_time"))
                .build();
        paymentService.handlePaymentNotification(midtransNotificationRequest);
        return ResponseUtil.buildResponse(HttpStatus.OK, "Notification successfully processed", null);
    }
}
