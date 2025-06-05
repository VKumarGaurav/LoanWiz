package com.loan.approve.dto;

import com.loan.approve.util.LoanStatus;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class LoanApplicationResponse {

    private Long applicationId;
    private BigDecimal amount;
    private String purpose;
    private BigDecimal annualIncome;
    private String employment;
    private String collateral;
    private LoanStatus status;
    private Instant createdAt;
    private Instant updatedAt;

}

