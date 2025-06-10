package com.loan.approve.dto;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ApprovalRequestDTO {
    private Long loanId;
    private Long approverId;
    private int level;
    private String comments;
    private String status; // "APPROVED" or "REJECTED"
}
