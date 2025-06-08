package com.loan.approve.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class RecommendedLoanTerms {
    private BigDecimal approvedAmount;
    private BigDecimal maxEligibleAmount;
    private BigDecimal interestRate;
    private Period loanTerm;
    private String repaymentFrequency; // MONTHLY, BIWEEKLY, etc.
    private BigDecimal estimatedMonthlyPayment;
    private String recommendationType; // PRIMARY, ALTERNATIVE
    private List<LoanTermOption> alternativeOptions;

    // Add list for alternative loan terms (in months)
    private List<Integer> alternativeTerms = new ArrayList<>();

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @ToString
    @Builder
    public static class LoanTermOption {
        private BigDecimal amount;
        private BigDecimal rate;
        private Period term;
        private String description;
        // getters & setters
    }

}
