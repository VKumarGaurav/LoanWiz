package com.loan.approve.dto;


import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CreditBureauResponse {
    private int score;
    private String scoreType;
    private List<String> negativeFactors;
    private List<CreditAccount> accounts;

    @Builder
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString    public static class CreditAccount {
        private String accountType;
        private String creditorName;
        private BigDecimal balance;
        private String status;
    }
}
