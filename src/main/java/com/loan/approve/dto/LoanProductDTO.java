package com.loan.approve.dto;

import lombok.*;

import java.math.BigDecimal;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class LoanProductDTO {
    private Long id;
    private String productCode;
    private String productName;
    private String description;
    private BigDecimal minInterestRate;
    private BigDecimal maxInterestRate;
    private Integer minTermMonths;
    private Integer maxTermMonths;
    private boolean active;

    public LoanProductDTO(long l, String homeLoan, double v, int i) {
    }


    // Getters and setters
}