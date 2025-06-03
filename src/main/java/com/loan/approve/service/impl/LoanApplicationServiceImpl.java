package com.loan.approve.service.impl;

import com.loan.approve.dto.LoanApplicationRequest;
import com.loan.approve.dto.LoanApplicationResponse;
import com.loan.approve.dto.LoanApplicationResultResponse;
import com.loan.approve.entity.LoanApplication;
import com.loan.approve.entity.User;
import com.loan.approve.repository.LoanApplicationRepository;
import com.loan.approve.repository.UserRepository;
import com.loan.approve.service.services.LoanApplicationService;
import com.loan.approve.util.LoanStatus;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class LoanApplicationServiceImpl implements LoanApplicationService {

    @Autowired private UserRepository userRepository;
    @Autowired private LoanApplicationRepository loanApplicationRepository;

    @Override
    public LoanApplicationResponse applyLoan(LoanApplicationRequest request, Long userId) {
        // Validate user exists
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));

        // Create LoanApplication
        LoanApplication loan = new LoanApplication();
        loan.setUser(user);
        loan.setAmount(request.getAmount());
        loan.setPurpose(request.getPurpose());
        loan.setAnnualIncome(request.getAnnualIncome());
        loan.setEmployment(request.getEmployment());
        loan.setCollateral(request.getCollateral());
        loan.setStatus(LoanStatus.PENDING);
        loan.setCreatedAt(Instant.now());
        loan.setUpdatedAt(Instant.now());

        LoanApplication savedLoan = loanApplicationRepository.save(loan);

        return mapToResponse(savedLoan);
    }

    @Override
    public List<LoanApplicationResponse> getUserLoanApplications(Long userId) {
        List<LoanApplication> loans = loanApplicationRepository.findByUserId(userId);
        return loans.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<LoanApplicationResponse> getAllLoanApplications() {
        List<LoanApplication> loans = loanApplicationRepository.findAll();
        return loans.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public LoanApplicationResponse getLoanApplicationById(Long applicationId) {
        LoanApplication loan = loanApplicationRepository.findById(applicationId)
                .orElseThrow(() -> new RuntimeException("Loan application not found with ID: " + applicationId));
        return mapToResponse(loan);
    }

    @Override
    public LoanApplicationResultResponse approveLoanApplication(Long applicationId) {
        LoanApplication loan = loanApplicationRepository.findById(applicationId)
                .orElseThrow(() -> new RuntimeException("Loan application not found with ID: " + applicationId));
        loan.setStatus(LoanStatus.APPROVED);
        loan.setUpdatedAt(Instant.now());
        loanApplicationRepository.save(loan);
        return LoanApplicationResultResponse.builder()
                .loanId(applicationId)
                .loanStatus(LoanStatus.APPROVED)
                .message("Accepted")
                .build();
    }

    @Override
    public LoanApplicationResultResponse rejectLoanApplication(Long applicationId) {
        LoanApplication loan = loanApplicationRepository.findById(applicationId)
                .orElseThrow(() -> new RuntimeException("Loan application not found with ID: " + applicationId));
        loan.setStatus(LoanStatus.REJECTED);
        loan.setUpdatedAt(Instant.now());
        loanApplicationRepository.save(loan);
        return LoanApplicationResultResponse.builder()
                .loanId(applicationId)
                .loanStatus(LoanStatus.REJECTED)
                .message("Not Eligible")
                .build();
    }

    // Helper method to map entity to DTO
    private LoanApplicationResponse mapToResponse(LoanApplication loan) {
        return new LoanApplicationResponse(
                loan.getId(),
                loan.getAmount(),
                loan.getPurpose(),
                loan.getAnnualIncome(),
                loan.getEmployment(),
                loan.getCollateral(),
                loan.getStatus(),
                loan.getCreatedAt(),
                loan.getUpdatedAt()
        );
    }

    // If you have a second applyLoan method (without userId), it could be redundant and removed.
    @Override
    public LoanApplicationResponse applyLoan(LoanApplicationRequest loanApplicationRequest) {
        throw new UnsupportedOperationException("Use applyLoan with userId instead.");
    }
}
