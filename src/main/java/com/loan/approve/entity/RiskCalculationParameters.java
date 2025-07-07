package com.loan.approve.entity;


import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "risk_calculation_parameters")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RiskCalculationParameters {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_id", unique = true)
    private Long productId;

    @Column(name = "credit_score_weight")
    private BigDecimal creditScoreWeight;

    @Column(name = "dti_weight")
    private BigDecimal dtiWeight;

    @Column(name = "income_weight")
    private BigDecimal incomeWeight;

    @Column(name = "assets_weight")
    private BigDecimal assetsWeight;

    @Column(name = "employment_stability_weight")
    private BigDecimal employmentStabilityWeight;


}