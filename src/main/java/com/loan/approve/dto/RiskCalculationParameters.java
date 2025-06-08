package com.loan.approve.dto;


import lombok.*;

import java.math.BigDecimal;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RiskCalculationParameters {
    private BigDecimal creditScoreWeight;
    private BigDecimal dtiWeight;
    private BigDecimal incomeWeight;
    private BigDecimal assetsWeight;
    private BigDecimal employmentStabilityWeight;

}