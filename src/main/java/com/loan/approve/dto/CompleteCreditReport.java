package com.loan.approve.dto;


import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@ToString
@Builder
public class CompleteCreditReport {
    private Long applicantId;
    private LocalDate reportDate;
    private List<CreditAccount> creditAccounts;
    private List<CreditInquiry> recentInquiries;
    private List<PublicRecord> publicRecords;
    private int oldestAccountAgeMonths;
    private int newestAccountAgeMonths;
    private int totalActiveAccounts;
    private int totalClosedAccounts;

    // Nested DTOs
    @Getter
    @Setter
    @AllArgsConstructor
    @ToString
    @Builder
    public static class CreditAccount {
        private String accountType;
        private String creditorName;
        private BigDecimal currentBalance;
        private BigDecimal creditLimit;
        private String paymentStatus;
        // getters & setters
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @ToString
    @Builder
    public static class CreditInquiry {
        private LocalDate inquiryDate;
        private String creditorName;
        private String inquiryType;
        // getters & setters
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @ToString
    @Builder
    public static class PublicRecord {
        private String recordType;
        private LocalDate filingDate;
        private BigDecimal amount;
        // getters & setters
    }

}
