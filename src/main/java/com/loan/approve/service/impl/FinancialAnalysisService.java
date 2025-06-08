package com.loan.approve.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.loan.approve.dto.*;
import com.loan.approve.dto.FinancialDataDto;
import com.loan.approve.entity.LoanApplication;
import com.loan.approve.entity.LoanProduct;
import com.loan.approve.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class FinancialAnalysisService {

    public static BigDecimal calculateLiquidityRatio(User applicant) {
        BigDecimal liquidAssets = applicant.getLiquidAssets();
        BigDecimal currentLiabilities = applicant.getCurrentLiabilities();

        if (liquidAssets == null || currentLiabilities == null || currentLiabilities.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        }

        return liquidAssets.divide(currentLiabilities, 2, RoundingMode.HALF_UP);
    }

    public static BigDecimal calculateSavingsRatio(FinancialDataDto financialData) {
        BigDecimal savings = financialData.getSavings();
        BigDecimal grossIncome = financialData.getGrossIncome();

        if (savings == null || grossIncome == null || grossIncome.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        }

        return savings.divide(grossIncome, 2, RoundingMode.HALF_UP);
    }
    public static String determineFinancialHealthRating(int creditScore, BigDecimal dtiRatio, BigDecimal liquidityRatio) {
        if (creditScore >= 750 && dtiRatio.compareTo(new BigDecimal("0.35")) < 0 && liquidityRatio.compareTo(new BigDecimal("2.0")) >= 0) {
            return "Excellent";
        } else if (creditScore >= 700 && dtiRatio.compareTo(new BigDecimal("0.40")) < 0 && liquidityRatio.compareTo(new BigDecimal("1.5")) >= 0) {
            return "Good";
        } else if (creditScore >= 650 && dtiRatio.compareTo(new BigDecimal("0.45")) < 0 && liquidityRatio.compareTo(new BigDecimal("1.0")) >= 0) {
            return "Fair";
        } else {
            return "Poor";
        }
    }

    public static BigDecimal calculateHealthScore(int creditScore, BigDecimal dtiRatio, BigDecimal liquidityRatio, BigDecimal savingsRatio) {
        // Sample: weighted average
        BigDecimal creditScoreComponent = BigDecimal.valueOf(creditScore).divide(new BigDecimal("10"), 2, BigDecimal.ROUND_HALF_UP); // scaled 0-85
        BigDecimal dtiComponent = BigDecimal.valueOf(50).subtract(dtiRatio.multiply(new BigDecimal("100"))); // lower DTI is better
        BigDecimal liquidityComponent = liquidityRatio.multiply(new BigDecimal("10"));
        BigDecimal savingsComponent = savingsRatio.multiply(new BigDecimal("20"));

        // Sum all components
        BigDecimal rawScore = creditScoreComponent.add(dtiComponent).add(liquidityComponent).add(savingsComponent);
        // Cap between 0 and 100
        if (rawScore.compareTo(BigDecimal.ZERO) < 0) {
            return BigDecimal.ZERO;
        } else if (rawScore.compareTo(new BigDecimal("100")) > 0) {
            return new BigDecimal("100");
        } else {
            return rawScore.setScale(2, BigDecimal.ROUND_HALF_UP);
        }
    }

    public static List<String> identifyPositiveFactors(CreditScoreResponse creditScore, DebtToIncomeRatio dti, FinancialDataDto financialData) {
        List<String> positives = new ArrayList<>();
        if (creditScore.getScore() >= 750) {
            positives.add("High Credit Score");
        }
        if (dti.getRatio().compareTo(new BigDecimal("0.35")) < 0) {
            positives.add("Low Debt-to-Income Ratio");
        }
        if (financialData.getSavings() != null && financialData.getSavings().compareTo(new BigDecimal("50000")) > 0) {
            positives.add("Strong Savings");
        }
        return positives;
    }

    public static List<String> identifyNegativeFactors(CreditScoreResponse creditScore, DebtToIncomeRatio dti, FinancialDataDto financialData) {
        List<String> negatives = new ArrayList<>();
        if (creditScore.getScore() < 650) {
            negatives.add("Low Credit Score");
        }
        if (dti.getRatio().compareTo(new BigDecimal("0.45")) > 0) {
            negatives.add("High Debt-to-Income Ratio");
        }
        if (financialData.getGrossIncome() != null && financialData.getGrossIncome().compareTo(new BigDecimal("20000")) < 0) {
            negatives.add("Low Gross Income");
        }
        return negatives;
    }

    public static boolean checkForRedFlags(CreditScoreResponse creditScore, FinancialDataDto financialData) {
        if (creditScore.getScore() < 600) {
            return true;
        }
        if (financialData.getSavings() != null && financialData.getSavings().compareTo(BigDecimal.ZERO) <= 0) {
            return true;
        }
        return false;
    }

    // Calculate Approved Amount
    public static BigDecimal calculateApprovedAmount(LoanApplication application, RiskAssessmentResult riskAssessment) {
        BigDecimal requestedAmount = application.getRequestedAmount();
        BigDecimal riskScoreDecimal = riskAssessment.getRiskScore();  // already BigDecimal

        BigDecimal riskFactor = BigDecimal.ONE.subtract(
                riskScoreDecimal.divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP)
        );

        BigDecimal approvedAmount = requestedAmount.multiply(riskFactor);

        return approvedAmount.setScale(2, RoundingMode.HALF_UP);
    }

    // Calculate Interest Rate
    public static BigDecimal calculateInterestRate(BigDecimal riskScore) {
        // Base rate of 8%
        BigDecimal baseRate = BigDecimal.valueOf(8.0);
        BigDecimal riskPremium = riskScore.multiply(BigDecimal.valueOf(0.05));
        return baseRate.add(riskPremium).setScale(2, RoundingMode.HALF_UP);
    }

    // Calculate Loan Term
    public static int calculateLoanTerm(String termOptionsStr, RiskAssessmentResult riskAssessment) {
        int riskScore = riskAssessment.getRiskScore().intValue();

        if (termOptionsStr == null || termOptionsStr.isEmpty()) {
            return 12; // default
        }

        // Parse comma-separated string into List<Integer>
        List<Integer> termOptions = Arrays.stream(termOptionsStr.split(","))
                .map(String::trim)
                .map(Integer::parseInt)
                .collect(Collectors.toList());

        Collections.sort(termOptions);

        if (riskScore >= 750) {
            return termOptions.get(termOptions.size() - 1); // longest
        } else if (riskScore >= 650) {
            return termOptions.get(termOptions.size() / 2); // middle
        } else {
            return termOptions.get(0); // shortest
        }
    }

    public static void addAlternativeTerms(RecommendedLoanTerms terms, LoanApplication application,
                                           RiskAssessmentResult riskAssessment) {
        // Assuming getLoanProduct() returns a single LoanProduct:
        LoanProduct loanProduct = (LoanProduct) application.getLoanProduct();
        String allTermsStr = loanProduct.getTermOptions();  // comma-separated string e.g. "12,24,36"

        if (allTermsStr == null || allTermsStr.isEmpty()) {
            return;
        }

        // Get primary term in months from Period
        int primaryTerm = terms.getLoanTerm().getYears() * 12 + terms.getLoanTerm().getMonths();
        // Or simply:
        // int primaryTerm = (int) terms.getLoanTerm().toTotalMonths();

        // Parse all terms from String to Integer list
        List<Integer> allTerms = Arrays.stream(allTermsStr.split(","))
                .map(String::trim)
                .map(Integer::parseInt)
                .collect(Collectors.toList());

        // Filter out primary term and sort alternatives
        List<Integer> alternatives = allTerms.stream()
                .filter(term -> term != primaryTerm)
                .sorted()
                .collect(Collectors.toList());

        terms.setAlternativeTerms(alternatives);
    }

}

