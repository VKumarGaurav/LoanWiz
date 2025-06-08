package com.loan.approve.service.impl;

import com.loan.approve.component.RiskCalculationEngine;
import com.loan.approve.config.CreditBureauConfig;
import com.loan.approve.dto.*;
import com.loan.approve.entity.Applicant;
import com.loan.approve.entity.LoanApplication;
import com.loan.approve.entity.LoanProduct;
import com.loan.approve.entity.User;
import com.loan.approve.repository.ApplicantRepository;
import com.loan.approve.repository.LoanApplicationRepository;
import com.loan.approve.service.service.CreditAssessmentService;
import com.loan.approve.util.CreditBureauType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Period;
import java.util.Collections;

@Service
@Slf4j
@Transactional
public class CreditAssessmentServiceImpl implements CreditAssessmentService {

    private final ApplicantRepository applicantRepository;
    private final LoanApplicationRepository loanApplicationRepository;
    private final RestTemplate restTemplate;
    private final CreditBureauConfig creditBureauConfig;
    private final RiskCalculationEngine riskCalculationEngine;

    @Autowired
    public CreditAssessmentServiceImpl(ApplicantRepository applicantRepository,
                                       LoanApplicationRepository loanApplicationRepository,
                                       RestTemplate restTemplate,
                                       CreditBureauConfig creditBureauConfig,
                                       RiskCalculationEngine riskCalculationEngine) {
        this.applicantRepository = applicantRepository;
        this.loanApplicationRepository = loanApplicationRepository;
        this.restTemplate = restTemplate;
        this.creditBureauConfig = creditBureauConfig;
        this.riskCalculationEngine = riskCalculationEngine;
    }

    @Override
    public CreditScoreResponse getCreditScore(Long applicantId, CreditBureauType bureauType) {
        Applicant applicant = applicantRepository.findById(applicantId)
                .orElseThrow(() -> new IllegalArgumentException("Applicant not found"));

        String bureauUrl = creditBureauConfig.getBureauEndpoint(bureauType);
        CreditBureauRequest request = new CreditBureauRequest(
                applicant.getSsn(),
                applicant.getFullName(),
                applicant.getDateOfBirth()
        );

        CreditBureauResponse bureauResponse = restTemplate.postForObject(
                bureauUrl,
                request,
                CreditBureauResponse.class
        );

        return mapToCreditScoreResponse(bureauResponse, bureauType);
    }

    @Override
    public RiskAssessmentResult performRiskAnalysis(Long applicationId) {
        LoanApplication application = loanApplicationRepository.findByIdWithApplicant(applicationId)
                .orElseThrow(() -> new IllegalArgumentException("Application not found"));

        CreditScoreResponse creditScore = getCreditScore(application.getUser().getId(), CreditBureauType.EQUIFAX);
        FinancialDataDto financialData = getFinancialData(application.getUser().getId());
        DebtToIncomeRatio dti = calculateDTI(financialData);

        return riskCalculationEngine.calculateRisk(
                application,
                creditScore,
                dti,
                financialData
        );
    }

    @Override
    public DebtToIncomeRatio calculateDTI(FinancialDataDto financialData) {
        BigDecimal totalMonthlyDebt = financialData.getMonthlyDebtPayments()
                .add(financialData.getRentMortgagePayment())
                .add(financialData.getOtherObligations());

        BigDecimal ratio = totalMonthlyDebt.divide(financialData.getMonthlyIncome(), 4, RoundingMode.HALF_UP);

        String category = determineDTICategory(ratio);

        return new DebtToIncomeRatio(ratio, totalMonthlyDebt, financialData.getMonthlyIncome(), category);
    }

    @Override
    public FinancialHealthAssessment evaluateFinancialHealth(Long applicantId) {
        User applicant = (User) applicantRepository.findByIdWithFinancialData(applicantId)
                .orElseThrow(() -> new IllegalArgumentException("Applicant not found"));

        FinancialDataDto financialData = getFinancialData(applicantId);
        CreditScoreResponse creditScore = getCreditScore(applicantId, CreditBureauType.EQUIFAX);
        DebtToIncomeRatio dti = calculateDTI(financialData);

        BigDecimal liquidityRatio = FinancialAnalysisService.calculateLiquidityRatio(applicant);
        BigDecimal savingsRatio = FinancialAnalysisService.calculateSavingsRatio(financialData);

        return FinancialHealthAssessment.builder()
                .overallRating(FinancialAnalysisService.determineFinancialHealthRating(creditScore.getScore(), dti.getRatio(), liquidityRatio))
                .healthScore(FinancialAnalysisService.calculateHealthScore(creditScore.getScore(), dti.getRatio(), liquidityRatio, savingsRatio))
                .positiveFactors(FinancialAnalysisService.identifyPositiveFactors(creditScore, dti, financialData))
                .negativeFactors(FinancialAnalysisService.identifyNegativeFactors(creditScore, dti, financialData))
                .liquidityRatio(liquidityRatio)
                .savingsRatio(savingsRatio)
                .redFlagsPresent(FinancialAnalysisService.checkForRedFlags(creditScore, financialData))
                .build();
    }

    @Override
    public CompleteCreditReport getFullCreditReport(Long applicantId) {
        // Implementation would integrate with credit bureau APIs
        // and combine data from multiple sources
        return null; // Actual implementation would return populated report
    }

    @Override
    public EligibilityResult checkBasicEligibility(Long applicationId) {
        LoanApplication application = loanApplicationRepository.findByIdWithApplicant(applicationId)
                .orElseThrow(() -> new IllegalArgumentException("Application not found"));

        CreditScoreResponse creditScore = getCreditScore(application.getUser().getId(), CreditBureauType.EQUIFAX);
        FinancialDataDto financialData = getFinancialData(application.getUser().getId());
        DebtToIncomeRatio dti = calculateDTI(financialData);

        EligibilityResult result = new EligibilityResult();
        result.setEligible(true); // Default to eligible

        // Check minimum credit score
        if (creditScore.getScore() < application.getLoanProduct().getMinCreditScore()) {
            result.addFailedCriteria(
                    "Minimum Credit Score",
                    String.valueOf(application.getLoanProduct().getMinCreditScore()),
                    String.valueOf(creditScore.getScore())
            );
            result.setEligible(false);
        }

        // Check maximum DTI ratio
        if (dti.getRatio().compareTo(application.getLoanProduct().getMaxDTI()) > 0) {
            result.addFailedCriteria(
                    "Maximum Debt-to-Income Ratio",
                    application.getLoanProduct().getMaxDTI().toString(),
                    dti.getRatio().toString()
            );
            result.setEligible(false);
        }

        // Additional eligibility checks...

        return result;
    }

    @Override
    public RecommendedLoanTerms getRecommendedTerms(Long applicationId) {
        LoanApplication application = loanApplicationRepository.findByIdWithApplicant(applicationId)
                .orElseThrow(() -> new IllegalArgumentException("Application not found"));

        RiskAssessmentResult riskAssessment = performRiskAnalysis(applicationId);
        FinancialHealthAssessment healthAssessment = evaluateFinancialHealth(application.getUser().getId());
        // get termOptions string from LoanProduct
        LoanProduct loanProduct = (LoanProduct) application.getLoanProduct();
        String allTermsStr = loanProduct.getTermOptions();
        String termOptions = loanProduct.getTermOptions();

        // calculate loan term from String and risk
        int loanTermMonths = FinancialAnalysisService.calculateLoanTerm(termOptions, riskAssessment);

        // Base recommendation
        RecommendedLoanTerms terms = new RecommendedLoanTerms();
        terms.setApprovedAmount(FinancialAnalysisService.calculateApprovedAmount(application, riskAssessment));
        terms.setInterestRate(FinancialAnalysisService.calculateInterestRate((riskAssessment.getRiskScore())));
        terms.setLoanTerm(Period.ofMonths(loanTermMonths));
        terms.setRepaymentFrequency("MONTHLY");
        terms.setRecommendationType("PRIMARY");

        // Add alternative options
        FinancialAnalysisService.addAlternativeTerms(terms, application, riskAssessment);

        return terms;
    }

    // Helper methods
    private CreditScoreResponse mapToCreditScoreResponse(CreditBureauResponse bureauResponse, CreditBureauType bureauType) {
        return new CreditScoreResponse(
                bureauResponse.getScore(),
                bureauType,
                bureauResponse.getScoreType(),
                LocalDate.now(),
                bureauResponse.getNegativeFactors()
        );
    }

    private FinancialDataDto getFinancialData(Long applicantId) {
        // Implementation would fetch from database or external systems
        return null; // Actual implementation would return populated data
    }

    private String determineDTICategory(BigDecimal ratio) {
        if (ratio.compareTo(new BigDecimal("0.35")) <= 0) return "GOOD";
        if (ratio.compareTo(new BigDecimal("0.50")) <= 0) return "ACCEPTABLE";
        return "POOR";
    }

}
