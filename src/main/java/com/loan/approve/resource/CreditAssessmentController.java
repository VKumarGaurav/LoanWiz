package com.loan.approve.resource;

import com.loan.approve.dto.*;
import com.loan.approve.service.service.CreditAssessmentService;
import com.loan.approve.util.CreditBureauType;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/credit-assessment")
@Api(tags = "Credit Assessment API", description = "Endpoints for credit risk assessment and financial analysis")
public class CreditAssessmentController {

    @Autowired
    private CreditAssessmentService creditAssessmentService;

    @ApiOperation(value = "Get Credit Score", notes = "Fetches the credit score of an applicant from the specified credit bureau")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved credit score"),
            @ApiResponse(code = 404, message = "Applicant not found"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @GetMapping("/credit-score/{applicantId}")
    public ResponseEntity<CreditScoreResponse> getCreditScore(
            @ApiParam(value = "Applicant ID", required = true) @PathVariable Long applicantId,
            @ApiParam(value = "Credit Bureau Type", required = true, allowableValues = "EQUIFAX, EXPERIAN, TRANSUNION")
            @RequestParam CreditBureauType bureauType) {
        CreditScoreResponse response = creditAssessmentService.getCreditScore(applicantId, bureauType);
        return ResponseEntity.ok(response);
    }

    @ApiOperation(value = "Perform Risk Analysis", notes = "Performs risk analysis for a loan application")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully performed risk analysis"),
            @ApiResponse(code = 404, message = "Application not found"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @GetMapping("/risk-analysis/{applicationId}")
    public ResponseEntity<RiskAssessmentResult> performRiskAnalysis(
            @ApiParam(value = "Application ID", required = true) @PathVariable Long applicationId) {
        RiskAssessmentResult result = creditAssessmentService.performRiskAnalysis(applicationId);
        return ResponseEntity.ok(result);
    }

    @ApiOperation(value = "Calculate DTI", notes = "Calculates the Debt-to-Income Ratio for given financial data")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully calculated DTI"),
            @ApiResponse(code = 400, message = "Invalid financial data"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @PostMapping("/calculate-dti")
    public ResponseEntity<DebtToIncomeRatio> calculateDTI(
            @ApiParam(value = "Financial Data", required = true)
            @RequestBody FinancialDataDto financialData) {
        DebtToIncomeRatio dti = creditAssessmentService.calculateDTI(financialData);
        return ResponseEntity.ok(dti);
    }

    @ApiOperation(value = "Evaluate Financial Health", notes = "Evaluates the financial health of an applicant")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully evaluated financial health"),
            @ApiResponse(code = 404, message = "Applicant not found"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @GetMapping("/financial-health/{applicantId}")
    public ResponseEntity<FinancialHealthAssessment> evaluateFinancialHealth(
            @ApiParam(value = "Applicant ID", required = true) @PathVariable Long applicantId) {
        FinancialHealthAssessment assessment = creditAssessmentService.evaluateFinancialHealth(applicantId);
        return ResponseEntity.ok(assessment);
    }

    @ApiOperation(value = "Get Recommended Loan Terms", notes = "Fetches recommended loan terms based on risk and financial health")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved recommended loan terms"),
            @ApiResponse(code = 404, message = "Application not found"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @GetMapping("/recommended-terms/{applicationId}")
    public ResponseEntity<RecommendedLoanTerms> getRecommendedTerms(
            @ApiParam(value = "Application ID", required = true) @PathVariable Long applicationId) {
        RecommendedLoanTerms terms = creditAssessmentService.getRecommendedTerms(applicationId);
        return ResponseEntity.ok(terms);
    }

    @ApiOperation(value = "Check Basic Eligibility", notes = "Performs basic eligibility check for a loan application")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully checked eligibility"),
            @ApiResponse(code = 404, message = "Application not found"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @GetMapping("/eligibility/{applicationId}")
    public ResponseEntity<EligibilityResult> checkBasicEligibility(
            @ApiParam(value = "Application ID", required = true) @PathVariable Long applicationId) {
        EligibilityResult result = creditAssessmentService.checkBasicEligibility(applicationId);
        return ResponseEntity.ok(result);
    }
}

