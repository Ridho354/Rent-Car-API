package com.enigma.carrent.service;

import com.enigma.carrent.dto.request.MidtransNotificationRequest;
import com.enigma.carrent.dto.response.PaymentResponse;

public interface PaymentService {
    PaymentResponse createPayment(String orderId);
    void handlePaymentNotification(MidtransNotificationRequest request); 
}
