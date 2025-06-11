package com.loan.approve.entity;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AmortizationSchedule {
    private int installmentNumber;
    private LocalDate paymentDate;
    private BigDecimal principalComponent;
    private BigDecimal interestComponent;
    private BigDecimal remainingBalance;
}

//Since amortization is generally calculated dynamically,
// we may not store it persistently, but let’s define a POJO for it.