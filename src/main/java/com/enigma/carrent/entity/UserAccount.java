package com.enigma.carrent.entity;

import com.enigma.carrent.constant.Constant;
import com.enigma.carrent.constant.UserRole;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// import org.springframework.security.core.GrantedAuthority;
// import org.springframework.security.core.authority.SimpleGrantedAuthority;
// import org.springframework.security.core.userdetails.UserDetails;

// import java.util.Collection;
// import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = Constant.TABLE_USER_ACCOUNT)
public class UserAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private String id;

    @Column(unique = true, nullable = false, length = 20)
    private String username;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name =" role")
    private UserRole role;
    
    @OneToOne
    @JsonIgnore
    private Customer customer;

    // @Override
    // public Collection<? extends GrantedAuthority> getAuthorities() {
    //     return List.of(new SimpleGrantedAuthority(role.name()));
    // }

    // @Override
    // public boolean isAccountNonExpired() {
    //     return UserDetails.super.isAccountNonExpired();
    // }

    // @Override
    // public boolean isAccountNonLocked() {
    //     return UserDetails.super.isAccountNonLocked();
    // }

    // @Override
    // public boolean isCredentialsNonExpired() {
    //     return UserDetails.super.isCredentialsNonExpired();
    // }

    // @Override
    // public boolean isEnabled() {
    //     return UserDetails.super.isEnabled();
    // }
}
