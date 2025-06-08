package com.loan.approve.service.service;

import com.loan.approve.dto.LoanApplicationRequest;
import com.loan.approve.dto.LoanApplicationResponse;
import com.loan.approve.dto.LoanApplicationResultResponse;

import java.util.List;

public interface LoanApplicationService {
    // User methods
    LoanApplicationResponse applyLoan(LoanApplicationRequest request, Long userId);

    LoanApplicationResponse applyLoan(LoanApplicationRequest loanApplicationRequest);

    List<LoanApplicationResponse> getUserLoanApplications(Long userId);

    // Admin methods
    List<LoanApplicationResponse> getAllLoanApplications();
    LoanApplicationResponse getLoanApplicationById(Long applicationId);
    LoanApplicationResultResponse approveLoanApplication(Long applicationId);
    LoanApplicationResultResponse rejectLoanApplication(Long applicationId);
}

