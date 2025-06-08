package com.loan.approve.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class EligibilityResult {
    private boolean isEligible;
    private String primaryReason;
    private List<String> additionalReasons;
    private List<EligibilityCriteria> failedCriteria;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    @Builder
    public static class EligibilityCriteria {
        private String criteriaName;
        private String requiredValue;
        private String applicantValue;
        private boolean passed;
    }

    // Helper methods
    public void addFailedCriteria(String name, String required, String actual) {
        EligibilityCriteria criteria = new EligibilityCriteria();
        criteria.setCriteriaName(name);
        criteria.setRequiredValue(required);
        criteria.setApplicantValue(actual);
        criteria.setPassed(false);
        this.failedCriteria.add(criteria);
    }
}
