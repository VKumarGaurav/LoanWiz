package com.loan.approve.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Data
@Table(name = "loan_product_rules")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoanProductRules {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "product_id")
    private LoanProduct product;

    private BigDecimal excellentThreshold;
    private BigDecimal goodThreshold;
    private BigDecimal fairThreshold;

    private BigDecimal minimumApprovalScore;

    private boolean allowManualOverride;
    private BigDecimal maxManualOverrideAmount;

    private boolean requireCoSignerBelowScore;
    private BigDecimal coSignerThreshold;
}
