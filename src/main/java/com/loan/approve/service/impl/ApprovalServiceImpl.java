package com.loan.approve.service.impl;

import com.loan.approve.dto.ApprovalRequestDTO;
import com.loan.approve.dto.ApprovalResponseDTO;
import com.loan.approve.entity.Approval;
import com.loan.approve.entity.Loan;
import com.loan.approve.entity.User;
import com.loan.approve.repository.ApprovalRepository;
import com.loan.approve.repository.LoanRepository;
import com.loan.approve.repository.UserRepository;
import com.loan.approve.service.service.ApprovalService;
import com.loan.approve.util.ApprovalStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ApprovalServiceImpl implements ApprovalService {

    @Autowired private ApprovalRepository approvalRepository;
    @Autowired private LoanRepository loanRepository;
    @Autowired private UserRepository userRepository;

    @Override
    @Transactional
    public ApprovalResponseDTO submitApproval(ApprovalRequestDTO request) {
        Loan loan = loanRepository.findById(request.getLoanId())
                .orElseThrow(() -> new RuntimeException("Loan not found"));

        User approver = userRepository.findById(request.getApproverId())
                .orElseThrow(() -> new RuntimeException("Approver not found"));

        Approval approval = new Approval();
        approval.setLoan(loan);
        approval.setApprover(approver);
        approval.setLevel(request.getLevel());
        approval.setComments(request.getComments());
        approval.setStatus(ApprovalStatus.valueOf(request.getStatus().toUpperCase()));
        approval.setApprovedAt(LocalDateTime.now());

        approvalRepository.save(approval);

        // Optional: Update loan status if final approval reached
        if (approval.getStatus() == ApprovalStatus.APPROVED && approval.getLevel() == getFinalApprovalLevel()) {
            loan.setStatus("APPROVED");
            loanRepository.save(loan);
        }

        return mapToResponseDTO(approval);
    }

    @Override
    public List<ApprovalResponseDTO> getApprovalsByLoanId(Long loanId) {
        List<Approval> approvals = approvalRepository.findByLoanIdOrderByLevelAsc(loanId);
        return approvals.stream().map(this::mapToResponseDTO).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void performInitialScreening(Long loanId) {
        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new RuntimeException("Loan not found"));

        // Simplified business rule: auto-reject if amount > 500000
        Approval approval = new Approval();
        approval.setLoan(loan);
        approval.setApprover(null);  // System-generated
        approval.setLevel(0);
        approval.setApprovedAt(LocalDateTime.now());

        if (loan.getAmount() > 500000) {
            approval.setStatus(ApprovalStatus.AUTO_REJECTED);
            loan.setStatus("REJECTED");
        } else {
            approval.setStatus(ApprovalStatus.AUTO_APPROVED);
            loan.setStatus("PENDING_REVIEW");
        }

        approval.setComments("Automated Screening");
        approvalRepository.save(approval);
        loanRepository.save(loan);
    }

    @Override
    public List<ApprovalResponseDTO> getPendingApprovalsForUser(Long approverId) {
        List<Approval> approvals = approvalRepository.findByApproverIdAndStatus(
                approverId, ApprovalStatus.PENDING);
        return approvals.stream().map(this::mapToResponseDTO).collect(Collectors.toList());
    }

    private int getFinalApprovalLevel() {
        // For example, level 2 is the final approval stage.
        return 2;
    }

    private ApprovalResponseDTO mapToResponseDTO(Approval approval) {
        ApprovalResponseDTO dto = new ApprovalResponseDTO();
        dto.setId(approval.getId());
        dto.setLoanId(approval.getLoan().getId());
        dto.setApproverId(approval.getApprover() != null ? approval.getApprover().getId() : null);
        dto.setApproverName(approval.getApprover() != null ? approval.getApprover().getFullName() : "System");
        dto.setStatus(approval.getStatus().name());
        dto.setComments(approval.getComments());
        dto.setApprovedAt(approval.getApprovedAt());
        dto.setLevel(approval.getLevel());
        return dto;
    }
}

