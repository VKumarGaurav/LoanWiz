package com.loan.approve.dto;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ApprovalResponseDTO {
    private Long id;
    private Long loanId;
    private Long approverId;
    private String approverName;
    private String status;
    private String comments;
    private LocalDateTime approvedAt;
    private int level;
}
