package com.enigma.carrent.service;

import java.util.List;

import com.enigma.carrent.dto.request.RentStatusUpdate;
import com.enigma.carrent.dto.request.RentTransactionRequest;
import com.enigma.carrent.dto.response.RentTransactionResponse;

public interface RentTransactionService {
    RentTransactionResponse addRentTransaction(RentTransactionRequest rentTransactionRequest);
    RentTransactionResponse updateRentTransaction(String id, RentTransactionRequest rentTransactionRequest);
    void deleteRentTransaction(String id);
    RentTransactionResponse findRentTransactionById(String id);
    List<RentTransactionResponse> findAllRentTransactions();
    RentTransactionResponse updateStatus(String id, RentStatusUpdate rentStatusUpdate);
}
