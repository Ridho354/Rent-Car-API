package com.enigma.carrent.controller;
import com.enigma.carrent.constant.Constant;
import com.enigma.carrent.dto.request.CarRequest;
import com.enigma.carrent.dto.request.CarStatusUpdate;
import com.enigma.carrent.dto.response.CarResponse;
import com.enigma.carrent.entity.Car;
import com.enigma.carrent.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping(Constant.API_CAR)
public class CarController {
    @Autowired
    private final CarService carService;
    
    public CarController(CarService carService) {
        this.carService = carService;
    }

    @PostMapping
    public CarResponse addCar(@RequestBody CarRequest carRequest) {
        CarResponse car = carService.addCar(carRequest);
        return car;
    }

    @GetMapping
    public List<Car> findAll() {
        List<Car> cars = carService.findAll();
        return cars;
    }

    @GetMapping("/{id}")
    public Car findById(@PathVariable String id) {
        Car car = carService.findById(id);
        return car;
    }

    @PutMapping("/{id}")
    public CarResponse updateCar(@PathVariable String id, @RequestBody CarRequest carRequest) {
        CarResponse car = carService.updateCar(id, carRequest);
        return car;
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable String id) {
        carService.deleteById(id);
    }

    @PatchMapping("/{id}/status")
    public CarResponse updateStatus(@PathVariable String id ,@RequestBody CarStatusUpdate carRequest) {
        return carService.updateStatus(id, carRequest);
    }

    @GetMapping("/available")
    public List<Car> findAvailableCars() {
        List<Car> cars = carService.findAvailableCars();
        return cars;
    }
}