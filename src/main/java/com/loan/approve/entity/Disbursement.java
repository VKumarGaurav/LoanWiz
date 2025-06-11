package com.loan.approve.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "disbursements")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Disbursement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long loanApplicationId;

    private BigDecimal amount;

    private String bankAccountNumber;

    private String status; // e.g., PENDING, SUCCESS, FAILED

    private LocalDateTime disbursementDate;
}
