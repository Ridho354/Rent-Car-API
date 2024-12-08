package com.enigma.carrent.entity;

import com.enigma.carrent.constant.CarStatus;
import com.enigma.carrent.constant.Constant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = Constant.TABLE_CAR)
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String carId;

    @Column(name = "lisense_plate", nullable = false)
    private String lisensePlate;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "brand")
    private String brand;

    @Column(name = "model")
    private String model;

    @Column(name = "status", nullable = false)
    private CarStatus status;
}
