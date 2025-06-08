package com.loan.approve.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class RiskAssessment {
    private int riskScore;  // e.g., 300-850 or 0-100
    private String riskCategory;  // e.g., "LOW", "MEDIUM", "HIGH"
    // Optional: flags for red flags or specific risk notes
    private boolean hasRedFlags;
}
