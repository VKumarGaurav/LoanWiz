package com.loan.approve.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
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

    @OneToOne(mappedBy = "applicant", cascade = CascadeType.ALL)
    private FinancialProfile financialProfile;

    @OneToMany(mappedBy = "applicant")
    private List<LoanApplication> loanApplications;
}
