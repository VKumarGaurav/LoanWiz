package com.loan.approve.service.service;


import com.loan.approve.dto.ApprovalRequestDTO;
import com.loan.approve.dto.ApprovalResponseDTO;

import java.util.List;

public interface ApprovalService {
    ApprovalResponseDTO submitApproval(ApprovalRequestDTO request);
    List<ApprovalResponseDTO> getApprovalsByLoanId(Long loanId);
    void performInitialScreening(Long loanId);
    List<ApprovalResponseDTO> getPendingApprovalsForUser(Long approverId);
}
