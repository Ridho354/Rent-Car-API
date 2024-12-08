package com.enigma.carrent.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.enigma.carrent.constant.CarStatus;
import com.enigma.carrent.entity.Car;
@Repository
public interface CarRepository extends JpaRepository<Car, String> {
    Car findByLisensePlate(String lisensePlate);
    List<Car> findByStatus(CarStatus status);
}
