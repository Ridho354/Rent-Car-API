package com.enigma.carrent.controller;

import com.enigma.carrent.constant.Constant;
import com.enigma.carrent.dto.request.RentStatusUpdate;
import com.enigma.carrent.dto.request.RentTransactionRequest;
import com.enigma.carrent.dto.response.RentTransactionResponse;
import com.enigma.carrent.service.RentTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping(Constant.API_RENT_TRANSACTION)
public class RentTransactionController {
    private final RentTransactionService rentTransactionService;

    @Autowired
    public RentTransactionController(RentTransactionService rentTransactionService) {
        this.rentTransactionService = rentTransactionService;
    }

    @PostMapping
    public RentTransactionResponse addRentTransaction(@RequestBody RentTransactionRequest rentTransactionRequest) {
        return rentTransactionService.addRentTransaction(rentTransactionRequest);
    }

    @GetMapping
    public List<RentTransactionResponse> findAllRentTransactions() {
        return rentTransactionService.findAllRentTransactions();
    }

    @GetMapping("/{id}")
    public RentTransactionResponse findRentTransactionById(@PathVariable String id) {
        return rentTransactionService.findRentTransactionById(id);
    }

    @PutMapping("/{id}")
    public RentTransactionResponse updateRentTransaction(@PathVariable String id, @RequestBody RentTransactionRequest rentTransactionRequest) {
        return rentTransactionService.updateRentTransaction(id, rentTransactionRequest);
    }

    @DeleteMapping("/{id}")
    public void deleteRentTransaction(@PathVariable String id) {
        rentTransactionService.deleteRentTransaction(id);
    }

    @PutMapping("/{id}/status")
    public RentTransactionResponse updateStatus(@PathVariable String id, @RequestBody RentStatusUpdate rentStatusUpdate) {
        return rentTransactionService.updateStatus(id, rentStatusUpdate);
    }
}
