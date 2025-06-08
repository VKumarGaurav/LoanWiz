package com.loan.approve.dto;

import lombok.*;

import java.math.BigDecimal;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class FinancialData {
    private BigDecimal savings;
    private BigDecimal grossIncome;
}
