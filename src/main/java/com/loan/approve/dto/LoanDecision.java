package com.loan.approve.dto;


import jakarta.persistence.Embeddable;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Embeddable
@Data
public class LoanDecision {
    private LocalDateTime decisionDate;
    private String decisionBy;
    private BigDecimal approvedAmount;
    private BigDecimal approvedInterestRate;
    private Integer approvedTermMonths;
    private String decisionNotes;
}
