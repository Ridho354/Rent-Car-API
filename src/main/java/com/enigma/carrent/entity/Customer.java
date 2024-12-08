package com.enigma.carrent.entity;
import com.enigma.carrent.constant.Constant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
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
@Table(name = Constant.TABLE_CUSTOMER)
public class Customer {
    @Id
    @Column(name = "nik", nullable = false)
    private String nik;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "driving_license", nullable = false)
    private String driving_license;
    
    @Column(name = "phone", nullable = false)
    private String phone;
    
    @Column(name = "address", nullable = false)
    private String address;

    @OneToOne
    @JoinColumn(name = "user_account_id")
    private UserAccount userAccount;
}
