package com.loan.approve.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;

@Entity
@Table(name = "user")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 30)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;  // Should be stored hashed

    @Column(nullable = false)
    private String role;  // e.g., "USER" or "ADMIN"

    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    private Instant updatedAt;

    @Column(nullable = false)
    private Boolean isActive;

    @Column(precision = 19, scale = 2)  // adjust as needed
    private BigDecimal liquidAssets;

    @Column(precision = 19, scale = 2)
    private BigDecimal currentLiabilities;
}
























