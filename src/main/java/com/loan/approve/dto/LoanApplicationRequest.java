package com.loan.approve.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
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

}













