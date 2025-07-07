package com.loan.approve.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "applicants")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Applicant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String ssn;
    private String fullName;
    private LocalDate dateOfBirth;
    private String email;
    private String phoneNumber;
    private BigDecimal liquidAssets;
    private BigDecimal currentLiabilities;


    // Correct relationship mapping
    @OneToOne(mappedBy = "applicant", cascade = CascadeType.ALL, orphanRemoval = true)
    private FinancialProfile financialProfile;

    @OneToMany(mappedBy = "applicant", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LoanApplication> loanApplications = new ArrayList<>();

    // Helper method to maintain consistency
    public void setFinancialProfile(FinancialProfile financialProfile) {
        if (financialProfile == null) {
            if (this.financialProfile != null) {
                this.financialProfile.setApplicant(null);
            }
        } else {
            financialProfile.setApplicant(this);
        }
        this.financialProfile = financialProfile;
    }

}

