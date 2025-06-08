package com.loan.approve.dto;

import lombok.*;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class FinancialDataDto {
    private BigDecimal monthlyIncome;
    private BigDecimal monthlyDebtPayments;
    private BigDecimal rentMortgagePayment;
    private BigDecimal otherObligations;
    private BigDecimal totalAssets;
    private BigDecimal savings;
    private BigDecimal grossIncome;
}
