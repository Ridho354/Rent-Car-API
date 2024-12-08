package com.enigma.carrent.service;

import java.util.List;

import com.enigma.carrent.dto.request.CarRequest;
import com.enigma.carrent.dto.request.CarStatusUpdate;
import com.enigma.carrent.dto.response.CarResponse;
import com.enigma.carrent.entity.Car;

public interface CarService {
    CarResponse addCar(CarRequest carRequest);
    List<Car> findAll();
    Car findById(String id);
    CarResponse updateCar(String id, CarRequest carRequest);
    void deleteById(String id);
    List<Car> findAvailableCars();
    CarResponse updateStatus(String id, CarStatusUpdate carRequest);
}
