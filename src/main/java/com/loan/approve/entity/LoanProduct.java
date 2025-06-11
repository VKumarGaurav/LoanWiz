package com.loan.approve.entity;


import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "loan_products")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class LoanProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String productCode;
    private String productName;
    private String description;

    private BigDecimal minLoanAmount;
    private BigDecimal maxLoanAmount;

    private Integer minTermMonths;
    private Integer maxTermMonths;

    private BigDecimal minInterestRate;
    private BigDecimal maxInterestRate;

    private int minCreditScore;
    private BigDecimal maxDTI;

    private String termOptions;
    private boolean active;

    private Integer termInMonths;

}
