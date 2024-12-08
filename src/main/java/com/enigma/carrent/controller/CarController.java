package com.enigma.carrent.controller;
import com.enigma.carrent.constant.Constant;
import com.enigma.carrent.dto.request.CarRequest;
import com.enigma.carrent.dto.request.CarStatusUpdate;
import com.enigma.carrent.dto.response.CarResponse;
import com.enigma.carrent.entity.Car;
import com.enigma.carrent.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(Constant.API_CAR)
public class CarController {
    @Autowired
    private final CarService carService;
    
    public CarController(CarService carService) {
        this.carService = carService;
    }

    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @PostMapping
    public CarResponse addCar(@RequestBody CarRequest carRequest) {
        CarResponse car = carService.addCar(carRequest);
        return car;
    }

    @PreAuthorize("hasAnyRole('CUSTOMER','ADMIN','SUPER_ADMIN')")
    @GetMapping
    public List<Car> findAll() {
        List<Car> cars = carService.findAll();
        return cars;
    }

    @PreAuthorize("hasAnyRole('CUSTOMER','ADMIN','SUPER_ADMIN')")
    @GetMapping("/{id}")
    public Car findById(@PathVariable String id) {
        Car car = carService.findById(id);
        return car;
    }

    @PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
    @PutMapping("/{id}")
    public CarResponse updateCar(@PathVariable String id, @RequestBody CarRequest carRequest) {
        CarResponse car = carService.updateCar(id, carRequest);
        return car;
    }

    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable String id) {
        carService.deleteById(id);
    }

    @PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
    @PatchMapping("/{id}/status")
    public CarResponse updateStatus(@PathVariable String id ,@RequestBody CarStatusUpdate carRequest) {
        return carService.updateStatus(id, carRequest);
    }

    @PreAuthorize("hasAnyRole('CUSTOMER','ADMIN','SUPER_ADMIN')")
    @GetMapping("/available")
    public List<Car> findAvailableCars() {
        List<Car> cars = carService.findAvailableCars();
        return cars;
    }
}