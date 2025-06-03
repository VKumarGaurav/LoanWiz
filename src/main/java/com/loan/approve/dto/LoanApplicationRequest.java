package com.loan.approve.dto;

import jakarta.validation.constraints.*;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public class LoanApplicationRequest {

    @NotNull
    @DecimalMin(value = "0.01", inclusive = true, message = "Loan amount must be greater than 0.")
    @DecimalMax(value = "1000000", inclusive = true, message = "Loan amount exceeds the maximum allowed.")
    private BigDecimal amount;

    @NotBlank
    @Size(max = 200)
    private String purpose;

    @NotNull
    @DecimalMin(value = "0.01", inclusive = true, message = "Annual income must be positive.")
    private BigDecimal annualIncome;

    @NotBlank
    @Pattern(regexp = "Full-time|Part-time|Self-employed|Unemployed", message = "Employment must be one of: Full-time, Part-time, Self-employed, Unemployed.")
    private String employment;

    @Size(max = 100)
    private String collateral;

    // Getters and setters...


    public LoanApplicationRequest() {
    }

    public LoanApplicationRequest(BigDecimal amount, String purpose, BigDecimal annualIncome, String employment, String collateral) {
        this.amount = amount;
        this.purpose = purpose;
        this.annualIncome = annualIncome;
        this.employment = employment;
        this.collateral = collateral;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public BigDecimal getAnnualIncome() {
        return annualIncome;
    }

    public void setAnnualIncome(BigDecimal annualIncome) {
        this.annualIncome = annualIncome;
    }

    public String getEmployment() {
        return employment;
    }

    public void setEmployment(String employment) {
        this.employment = employment;
    }

    public String getCollateral() {
        return collateral;
    }

    public void setCollateral(String collateral) {
        this.collateral = collateral;
    }

    @Override
    public String toString() {
        return "LoanApplicationRequest{" +
                "amount=" + amount +
                ", purpose='" + purpose + '\'' +
                ", annualIncome=" + annualIncome +
                ", employment='" + employment + '\'' +
                ", collateral='" + collateral + '\'' +
                '}';
    }
}
































