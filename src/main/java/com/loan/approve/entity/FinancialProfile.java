package com.loan.approve.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "financial_profiles")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class FinancialProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "applicant_id")
    private Applicant applicant;  // Owning side of the relationship

    private BigDecimal annualIncome;
    private BigDecimal monthlyIncome;
    private BigDecimal monthlyDebtPayments;
    private BigDecimal rentMortgagePayment;
    private BigDecimal otherObligations;

    private BigDecimal totalAssets;
    private BigDecimal liquidAssets;

    private String employmentStatus;
    private String employerName;
    private Integer employmentDurationMonths;
}
