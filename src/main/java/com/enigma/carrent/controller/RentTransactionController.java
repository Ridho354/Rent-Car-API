package com.enigma.carrent.controller;

import com.enigma.carrent.constant.Constant;
import com.enigma.carrent.dto.request.RentStatusUpdate;
import com.enigma.carrent.dto.request.RentTransactionRequest;
import com.enigma.carrent.dto.response.RentTransactionResponse;
import com.enigma.carrent.service.RentTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(Constant.API_RENT_TRANSACTION)
public class RentTransactionController {
    private final RentTransactionService rentTransactionService;

    public RentTransactionController(RentTransactionService rentTransactionService) {
        this.rentTransactionService = rentTransactionService;
    }

    @PreAuthorize("hasAnyRole('CUSTOMER','ADMIN','SUPER_ADMIN')")
    @PostMapping
    public RentTransactionResponse addRentTransaction(@RequestBody RentTransactionRequest rentTransactionRequest) {
        return rentTransactionService.addRentTransaction(rentTransactionRequest);
    }

    @PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
    @GetMapping
    public List<RentTransactionResponse> findAllRentTransactions() {
        return rentTransactionService.findAllRentTransactions();
    }

    @PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
    @GetMapping("/{id}")
    public RentTransactionResponse findRentTransactionById(@PathVariable String id) {
        return rentTransactionService.findRentTransactionById(id);
    }

    @PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
    @PutMapping("/{id}")
    public RentTransactionResponse updateRentTransaction(@PathVariable String id, @RequestBody RentTransactionRequest rentTransactionRequest) {
        return rentTransactionService.updateRentTransaction(id, rentTransactionRequest);
    }

    @PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
    @DeleteMapping("/{id}")
    public void deleteRentTransaction(@PathVariable String id) {
        rentTransactionService.deleteRentTransaction(id);
    }

    @PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
    @PutMapping("/{id}/status")
    public RentTransactionResponse updateStatus(@PathVariable String id, @RequestBody RentStatusUpdate rentStatusUpdate) {
        return rentTransactionService.updateStatus(id, rentStatusUpdate);
    }
}
