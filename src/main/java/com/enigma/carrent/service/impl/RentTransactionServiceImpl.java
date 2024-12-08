package com.enigma.carrent.service.impl;

import com.enigma.carrent.constant.CarStatus;
import com.enigma.carrent.constant.RentTransactionStatus;
import com.enigma.carrent.dto.request.CarStatusUpdate;
import com.enigma.carrent.dto.request.RentStatusUpdate;
import com.enigma.carrent.dto.request.RentTransactionRequest;
import com.enigma.carrent.dto.response.RentTransactionResponse;
import com.enigma.carrent.entity.RentTransaction;
import com.enigma.carrent.repository.RentTransactionRepository;
import com.enigma.carrent.service.CarService;
import com.enigma.carrent.service.CustomerService;
import com.enigma.carrent.service.RentTransactionService;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import com.enigma.carrent.entity.Customer;
import com.enigma.carrent.entity.Car;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class RentTransactionServiceImpl implements RentTransactionService {
    private final RentTransactionRepository rentTransactionRepository;
    private final CustomerService customerService;
    private final CarService carService;
    LocalDate now = LocalDate.now();

    @Override
    public RentTransactionResponse addRentTransaction(RentTransactionRequest request) {
        Customer customer = customerService.findById(request.getNik());
        Car car = carService.findById(request.getCarId());
        if (customer == null || car == null) {
            return null;
        }
        
        RentTransaction rentTransaction = RentTransaction.builder()
                .nik(customer)
                .car_id(car)
                .rental_start_date(request.getRentalStartDate())
                .rental_end_date(request.getRentalEndDate())
                .total_price(request.getTotalPrice())
                .status(request.getStatus())
                .created_at(now)
                .updated_at(now)
                .build();
        rentTransactionRepository.saveAndFlush(rentTransaction);

        Car selectCar = carService.findById(request.getCarId());
        CarStatusUpdate selectStatus = CarStatusUpdate.builder()
                .status(CarStatus.RENTED)
                .build();
        carService.updateStatus(request.getCarId(), selectStatus);
        
        return toResponse(rentTransaction);
    }

    @Override
    public List<RentTransactionResponse> findAllRentTransactions() {
        List<RentTransaction> rentTransactions = rentTransactionRepository.findAll();
        return toResponse(rentTransactions);
    }

    @Override
    public RentTransactionResponse findRentTransactionById(String id) {
        RentTransaction rentTransaction = rentTransactionRepository.findById(id).orElse(null);
        return toResponse(rentTransaction);
    }

    @Override
    public RentTransactionResponse updateRentTransaction(String id, RentTransactionRequest request) {
        RentTransaction rentTransaction = rentTransactionRepository.findById(id).orElse(null);
        Customer customer = customerService.findById(request.getNik());
        Car car = carService.findById(request.getCarId());
        if (rentTransaction != null || customer == null || car == null) {
        rentTransaction = RentTransaction.builder()
                .nik(customer)
                .car_id(car)
                .rental_start_date(request.getRentalStartDate())
                .rental_end_date(request.getRentalEndDate())
                .total_price(request.getTotalPrice())
                .status(request.getStatus())
                .updated_at(now)
                .build();
        rentTransactionRepository.saveAndFlush(rentTransaction);
        return toResponse(rentTransaction);
        }
        return null;
    }

    @Override
    public void deleteRentTransaction(String id) {
        rentTransactionRepository.deleteById(id);
    }

    private RentTransactionResponse toResponse(RentTransaction rentTransaction) {
        RentTransactionResponse rentTransactionResponse = new RentTransactionResponse();
        rentTransactionResponse.setId(rentTransaction.getId());
        rentTransactionResponse.setNik(rentTransaction.getNik().getNik());
        rentTransactionResponse.setCarId(rentTransaction.getCar_id().getCarId());
        rentTransactionResponse.setRentalStartDate(rentTransaction.getRental_start_date());
        rentTransactionResponse.setRentalEndDate(rentTransaction.getRental_end_date());
        rentTransactionResponse.setTotalPrice(rentTransaction.getTotal_price());
        rentTransactionResponse.setStatus(rentTransaction.getStatus());
        rentTransactionResponse.setCreatedAt(rentTransaction.getCreated_at());
        rentTransactionResponse.setUpdatedAt(rentTransaction.getUpdated_at());
        return rentTransactionResponse;
    }

    private List<RentTransactionResponse> toResponse(List<RentTransaction> rentTransactions) {
        List<RentTransactionResponse> rentTransactionResponses = new ArrayList<>();
        for (RentTransaction rentTransaction : rentTransactions) {
            rentTransactionResponses.add(toResponse(rentTransaction));
        }
        return rentTransactionResponses;
    }

    @Override
    public RentTransactionResponse updateStatus(String id, RentStatusUpdate rentStatusUpdate) {
        RentTransactionStatus status = rentStatusUpdate.getStatus();
        RentTransaction rentTransaction = rentTransactionRepository.findById(id).orElse(null);
        if (rentTransaction != null) {
            rentTransaction.setStatus(status);
            rentTransaction.setUpdated_at(now);
            rentTransactionRepository.saveAndFlush(rentTransaction);
            return toResponse(rentTransaction);
        }
        if (rentTransaction.getStatus() == RentTransactionStatus.COMPLETED) {
            Car selectCar = carService.findById(rentTransaction.getCar_id().getCarId());
            CarStatusUpdate selectStatus = CarStatusUpdate.builder()
                    .status(CarStatus.AVAILABLE)
                    .build();
            carService.updateStatus(selectCar.getCarId(), selectStatus);
        }

        return null;
    }
}