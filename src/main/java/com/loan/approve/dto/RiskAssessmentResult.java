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
public class RiskAssessmentResult {
    private String riskLevel;
    private BigDecimal riskScore;
    private List<String> riskFactors;
    private boolean approvalRecommendation;
}
