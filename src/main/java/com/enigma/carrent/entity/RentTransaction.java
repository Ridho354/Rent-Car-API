package com.enigma.carrent.entity;
import java.time.LocalDate;
import org.springframework.data.annotation.CreatedDate;

import com.enigma.carrent.constant.Constant;
import com.enigma.carrent.constant.RentTransactionStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = Constant.TABLE_RENT_TRANSACTION)
public class RentTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    @JoinColumn(name = "nik", nullable = false)
    private Customer nik;

    @ManyToOne
    @JoinColumn(name = "carId", nullable = false)
    private Car car_id;

    @Column(name = "rental_start_date", nullable = false)
    private LocalDate rental_start_date;
    @Column(name = "rental_end_date", nullable = false)
    private LocalDate rental_end_date;

    @Column(name = "total_price", nullable = false)
    private Long total_price;

    @Column(name = "status", nullable = false)
    private RentTransactionStatus status;

    @CreatedDate
    @Column(name = "created_at", nullable = false)
    private LocalDate created_at;

    @Column(name = "updated_at", nullable = false)
    private LocalDate updated_at;
}
