package com.loan.approve.component;

import com.loan.approve.dto.*;
import com.loan.approve.entity.LoanApplication;
import com.loan.approve.entity.LoanProductRules;
import com.loan.approve.repository.LoanProductRulesRepository;
import com.loan.approve.repository.RiskParametersRepository;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

@Component
public class RiskCalculationEngine {

    private final RiskParametersRepository riskParametersRepository;
    private final LoanProductRulesRepository loanProductRulesRepository;
    private final RiskCalculationParameters riskCalculationParameters;

    public RiskCalculationEngine(RiskParametersRepository riskParametersRepository,
                                 LoanProductRulesRepository loanProductRulesRepository, RiskCalculationParameters riskCalculationParameters) {
        this.riskParametersRepository = riskParametersRepository;
        this.loanProductRulesRepository = loanProductRulesRepository;
        this.riskCalculationParameters = riskCalculationParameters;
    }

    public RiskAssessmentResult calculateRisk(LoanApplication application,
                                              CreditScoreResponse creditScore,
                                              DebtToIncomeRatio dti,
                                              FinancialDataDto financialData) {

        // Get risk parameters for this loan product
        RiskCalculationParameters parameters = riskParametersRepository
                .findByProductId(application.getLoanProduct().getId())
                .orElseGet(RiskCalculationParameters::new);

        // Calculate risk score (0-100 scale)
        BigDecimal riskScore = calculateCompositeRiskScore(
                creditScore.getScore(),
                dti.getRatio(),
                financialData,
                parameters
        );

        // Get product-specific rules
        LoanProductRules rules = loanProductRulesRepository
                .findByProductId(application.getLoanProduct().getId())
                .orElseThrow(() -> new IllegalStateException("No rules found for product with ID: " +
                        application.getLoanProduct().getId()));

        // Determine risk level
        String riskLevel = determineRiskLevel(riskScore, rules);

        // Prepare result
        return RiskAssessmentResult.builder()
                .riskLevel(riskLevel)
                .riskScore(riskScore)
                .riskFactors(Collections.singletonList(String.valueOf(identifyRiskFactors(creditScore, dti, financialData, rules))))
                .approvalRecommendation(determineApprovalRecommendation(riskScore, rules))
                .build();
    }

    private BigDecimal calculateCompositeRiskScore(int creditScore,
                                                   BigDecimal dtiRatio,
                                                   FinancialDataDto financialData,
                                                   RiskCalculationParameters parameters) {
        // Normalize credit score (300-850 scale to 0-100)
        BigDecimal normalizedCreditScore = BigDecimal.valueOf((creditScore - 300) / 5.5);

        // Invert DTI ratio (higher DTI = higher risk)
        BigDecimal invertedDti = BigDecimal.valueOf(100).subtract(dtiRatio.multiply(BigDecimal.valueOf(100)));

        // Calculate income score (0-100 scale)
        BigDecimal incomeScore = calculateIncomeScore(financialData.getMonthlyIncome());

        // Calculate asset score (0-100 scale)
        BigDecimal assetScore = calculateAssetScore(financialData.getTotalAssets());

        // Weighted sum
        return normalizedCreditScore.multiply(parameters.getCreditScoreWeight())
                .add(invertedDti.multiply(parameters.getDtiWeight()))
                .add(incomeScore.multiply(parameters.getIncomeWeight()))
                .add(assetScore.multiply(parameters.getAssetsWeight()))
                .setScale(2, RoundingMode.HALF_UP);
    }

    private BigDecimal calculateIncomeScore(BigDecimal monthlyIncome) {
        // Example: $10,000 monthly income = 100 score, $5,000 = 50 score, capped at 100
        return monthlyIncome.divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP)
                .min(BigDecimal.valueOf(100));
    }

    private BigDecimal calculateAssetScore(BigDecimal totalAssets) {
        // Example: $1M assets = 100 score, $500k = 50 score, capped at 100
        return totalAssets.divide(BigDecimal.valueOf(10000), 2, RoundingMode.HALF_UP)
                .min(BigDecimal.valueOf(100));
    }

    private String determineRiskLevel(BigDecimal riskScore, LoanProductRules rules) {
        if (riskScore.compareTo(rules.getExcellentThreshold()) >= 0) return "EXCELLENT";
        if (riskScore.compareTo(rules.getGoodThreshold()) >= 0) return "GOOD";
        if (riskScore.compareTo(rules.getFairThreshold()) >= 0) return "FAIR";
        return "POOR";
    }

    private boolean determineApprovalRecommendation(BigDecimal riskScore, LoanProductRules rules) {
        return riskScore.compareTo(rules.getMinimumApprovalScore()) >= 0;
    }

    private List<String> identifyRiskFactors(CreditScoreResponse creditScore,
                                             DebtToIncomeRatio dti,
                                             FinancialDataDto financialData,
                                             LoanProductRules rules) {
        // Implementation would analyze all factors and return relevant risk factors
        return List.of(); // Actual implementation would return real factors
    }
}
