package com.loan.approve.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class DebtToIncomeRatio {
    private BigDecimal ratio;
    private BigDecimal monthlyDebt;
    private BigDecimal monthlyIncome;
    private String category; // GOOD, ACCEPTABLE, POOR, etc.
}
