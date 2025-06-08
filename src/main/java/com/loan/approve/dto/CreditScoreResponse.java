package com.loan.approve.dto;


import com.loan.approve.util.CreditBureauType;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class CreditScoreResponse {
    private int score;
    private CreditBureauType bureau;
    private String scoreType;
    private LocalDate reportDate;
    private List<String> negativeFactors;
}
