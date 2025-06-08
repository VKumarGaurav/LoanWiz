package com.loan.approve.service.service;


import com.loan.approve.dto.*;
import com.loan.approve.util.CreditBureauType;

public interface CreditAssessmentService {

    /**
     * Retrieves credit score from external credit bureaus
     * @param applicantId Unique identifier of the loan applicant
     * @param bureauType Credit bureau to query (EQUIFAX, EXPERIAN, TRANSUNION, etc.)
     * @return CreditScoreResponse containing score and details
     */
    CreditScoreResponse getCreditScore(Long applicantId, CreditBureauType bureauType);

    /**
     * Performs comprehensive risk analysis for loan application
     * @param applicationId Loan application ID
     * @return RiskAssessmentResult with risk score and analysis
     */
    RiskAssessmentResult performRiskAnalysis(Long applicationId);

    /**
     * Calculates debt-to-income ratio
     * @param financialData Applicant's financial information
     * @return DebtToIncomeRatio calculation result
     */
    DebtToIncomeRatio calculateDTI(FinancialDataDto financialData);

    /**
     * Evaluates overall financial health
     * @param applicantId Unique identifier of the applicant
     * @return FinancialHealthAssessment result
     */
    FinancialHealthAssessment evaluateFinancialHealth(Long applicantId);

    /**
     * Gets comprehensive credit report
     * @param applicantId Unique identifier of the applicant
     * @return CompleteCreditReport containing all credit information
     */
    CompleteCreditReport getFullCreditReport(Long applicantId);

    /**
     * Checks if applicant meets minimum credit requirements
     * @param applicationId Loan application ID
     * @return EligibilityResult with pass/fail and reasons
     */
    EligibilityResult checkBasicEligibility(Long applicationId);

    /**
     * Gets recommended loan terms based on credit assessment
     * @param applicationId Loan application ID
     * @return RecommendedLoanTerms with amounts, rates, and durations
     */
    RecommendedLoanTerms getRecommendedTerms(Long applicationId);
}
