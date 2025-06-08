package com.loan.approve.dto;

import lombok.*;
import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class FinancialHealthAssessment {
    private String overallRating; // EXCELLENT, GOOD, FAIR, POOR
    private BigDecimal healthScore;
    private List<String> positiveFactors;
    private List<String> negativeFactors;
    private BigDecimal liquidityRatio;
    private BigDecimal savingsRatio;
    private boolean redFlagsPresent;
}
