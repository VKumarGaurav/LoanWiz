package com.loan.approve.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DisbursementDTO {
    private Long id;
    private Long loanApplicationId;
    private BigDecimal amount;
    private String bankAccountNumber;
    private String status;
    private LocalDateTime disbursementDate;

    public DisbursementDTO(long l, long l1, String inProgress) {
    }
}