package com.enigma.carrent.service.impl;

import com.enigma.carrent.constant.CarStatus;
import com.enigma.carrent.dto.request.CarRequest;
import com.enigma.carrent.dto.request.CarStatusUpdate;
import com.enigma.carrent.dto.response.CarResponse;
import com.enigma.carrent.entity.Car;
import com.enigma.carrent.repository.CarRepository;
import com.enigma.carrent.service.CarService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarServiceImpl implements CarService {
    private final CarRepository carRepository;

    public CarServiceImpl(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    @Override
    public CarResponse addCar(CarRequest carRequest) {
        Car car = new Car();
        car.setLisensePlate(carRequest.getLisensePlate());
        car.setName(carRequest.getName());
        car.setBrand(carRequest.getBrand());
        car.setModel(carRequest.getModel());
        car.setStatus(CarStatus.AVAILABLE);
        carRepository.saveAndFlush(car);
        return toResponse(car);
    }

    @Override
    public List<Car> findAll() {
        return carRepository.findAll();
    }

    @Override
    public Car findById(String id) {
        Car car = carRepository.findById(id).orElse(null);
        return car;
    }

    @Override
    public CarResponse updateCar(String id, CarRequest carRequest) {
        Car car = carRepository.findById(id).orElse(null);
        if (car != null) {
            car.setLisensePlate(carRequest.getLisensePlate());
            car.setName(carRequest.getName());
            car.setBrand(carRequest.getBrand());
            car.setModel(carRequest.getModel());
            carRepository.saveAndFlush(car);
            return toResponse(car);
        }
        return null;
    }

    @Override
    public void deleteById(String id) {
        carRepository.deleteById(id);
    }

    public CarResponse toResponse(Car cars) {
        CarResponse carResponse = new CarResponse();
        carResponse.setLisensePlate(cars.getLisensePlate());
        carResponse.setName(cars.getName());
        carResponse.setBrand(cars.getBrand());
        carResponse.setModel(cars.getModel());
        carResponse.setStatus(cars.getStatus());
        return carResponse;
    }

    @Override
    public List<Car> findAvailableCars() {
        List<Car> car = carRepository.findByStatus(CarStatus.AVAILABLE);
        return car;
    }

    @Override
    public CarResponse updateStatus(String id, CarStatusUpdate carRequest) {
        Car car = carRepository.findById(id).orElse(null);
        if (car != null) {
            car.setStatus(carRequest.status);
            carRepository.saveAndFlush(car);
            return toResponse(car);
        }
        return null;
    }
}