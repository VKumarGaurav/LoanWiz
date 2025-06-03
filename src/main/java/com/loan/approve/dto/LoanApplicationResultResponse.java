package com.loan.approve.dto;

import com.loan.approve.util.LoanStatus;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class LoanApplicationResultResponse {

    private Long loanId;
    private LoanStatus loanStatus;
    private String message;
}
