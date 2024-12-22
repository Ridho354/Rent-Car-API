package com.enigma.carrent.service.impl;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClient;
import org.springframework.web.server.ResponseStatusException;

import com.enigma.carrent.constant.PaymentStatus;
import com.enigma.carrent.constant.RentTransactionStatus;
import com.enigma.carrent.dto.request.MidtransNotificationRequest;
import com.enigma.carrent.dto.request.MidtransPaymentRequest;
import com.enigma.carrent.dto.request.MidtransTransactionDetailRequest;
import com.enigma.carrent.dto.request.RentStatusUpdate;
import com.enigma.carrent.dto.response.MidtransSnapResponse;
import com.enigma.carrent.dto.response.PaymentResponse;
import com.enigma.carrent.entity.Car;
import com.enigma.carrent.entity.Payment;
import com.enigma.carrent.entity.RentTransaction;
import com.enigma.carrent.repository.PaymentRepository;
import com.enigma.carrent.service.PaymentService;
import com.enigma.carrent.service.RentTransactionService;

@Service
public class PaymentServiceImpl implements PaymentService {
    @Qualifier("midtransPaymentGatewayClient")
    private final RestClient midtransPaymentGatewayClient;
    private final PaymentRepository paymentRepository;
    private final RentTransactionService rentTransactionService;

    public PaymentServiceImpl(RestClient midtransPaymentGatewayClient, PaymentRepository paymentRepository, RentTransactionService rentTransactionService) {
        this.midtransPaymentGatewayClient = midtransPaymentGatewayClient;
        this.paymentRepository = paymentRepository;
        this.rentTransactionService = rentTransactionService;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PaymentResponse createPayment(String orderId) {
        System.out.println("========== 0 ==========");
        RentTransaction rentTransaction = rentTransactionService.getOne(orderId);
        if (rentTransaction.getStatus() != RentTransactionStatus.PENDING) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "RentTransaction status must be PENDING but current status is " + rentTransaction.getStatus());
        }

        // #Midtrans# 2 dapetin total amount dari rentTransaction, total masing-masing item dijumlah
        // bagaimana dapatkan total amount dari berbagai rentTransaction items/details? bisa pakai reduce bisa pakai
        // https://www.baeldung.com/java-stream-sum
        // List<OrderDetails> orderDetailList = rentTransaction.getOrderDetails();
        // Long totalAmount = orderDetailList.stream().mapToLong(orderDetail -> {
        //     Long totalAmountPerDetail = orderDetail.getMenu().getPrice() * orderDetail.getQuantity();
        //     return totalAmountPerDetail;
        // }).sum();

        Car car = rentTransaction.getCar_id();

        long totalAmount = 0;
        long numberOfDays = ChronoUnit.DAYS.between(rentTransaction.getRental_start_date(), rentTransaction.getRental_end_date());
        totalAmount = car.getPrice() * numberOfDays;

        System.out.println("========== * ==========");
        System.out.println("numberOfDays = " + numberOfDays);
        System.out.println("car price = " + car.getPrice());
        System.out.println("totalAmount = " + totalAmount);

        MidtransTransactionDetailRequest midtransTransactionDetailRequest = MidtransTransactionDetailRequest.builder()
                .orderId(orderId)
                .grossAmount(totalAmount)
                .build();
        MidtransPaymentRequest midtransRequest = MidtransPaymentRequest.builder()
                .transactionDetail(midtransTransactionDetailRequest)
                .enabledPayments(List.of("gopay", "shopeepay", "bca_va"))
                .build();

        MidtransSnapResponse midtransSnapResponse = midtransPaymentGatewayClient
                .post()
                .uri("/snap/v1/transactions")
                .body(midtransRequest)
                .retrieve()
                .body(MidtransSnapResponse.class);

        assert midtransSnapResponse != null;
        Payment payment = Payment.builder()
                .transactionId(rentTransaction)
                .amount(totalAmount)
                .status(PaymentStatus.PENDING)
                .tokenSnap(midtransSnapResponse.getToken()) 
                .redirectUrl(midtransSnapResponse.getRedirectUrl())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        
        paymentRepository.saveAndFlush(payment);
        
        return PaymentResponse.builder()
                .orderId(orderId)
                .amount(totalAmount)
                .paymentStatus(PaymentStatus.PENDING)
                .tokenSnap(midtransSnapResponse.getToken())
                .redirectUrl(midtransSnapResponse.getRedirectUrl())
                .build();
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void handlePaymentNotification(MidtransNotificationRequest request) {
        System.out.println("Transaction ID: " + request.getOrderId());
        Payment payment = paymentRepository.findByTransactionId(request.getOrderId());
        
        if (payment == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Payment not found");
        }
        PaymentStatus newPaymentStatus = PaymentStatus.fromStatus(request.getTransactionStatus());

        payment.setStatus(newPaymentStatus);
        payment.setUpdatedAt(LocalDateTime.now());
        paymentRepository.saveAndFlush(payment);

        if (newPaymentStatus == PaymentStatus.SETTLEMENT) {
            RentStatusUpdate updateOrderStatusToPaid = RentStatusUpdate.builder().status(RentTransactionStatus.PAID).build();
            rentTransactionService.updateStatus(request.getOrderId(), updateOrderStatusToPaid);
        } else if (
                newPaymentStatus == PaymentStatus.CANCEL ||
                newPaymentStatus == PaymentStatus.DENY ||
                newPaymentStatus == PaymentStatus.EXPIRE
            ) {
                RentStatusUpdate updateOrderStatusToPaid = RentStatusUpdate.builder().status(RentTransactionStatus.CANCELLED).build();
                rentTransactionService.updateStatus(request.getOrderId(), updateOrderStatusToPaid);
        }
    }
}