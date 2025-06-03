package com.loan.approve.resource;


import com.loan.approve.dto.LoanApplicationRequest;
import com.loan.approve.dto.LoanApplicationResponse;
import com.loan.approve.dto.LoanApplicationResultResponse;
import com.loan.approve.service.services.LoanApplicationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@Slf4j
@RequestMapping(path = "/loan/")
public class LoanApplicationController {

    @Autowired private LoanApplicationService loanApplicationService;

    // --- User APIs --- ----------------------------------------------------------------------------------

    @PostMapping("/user/apply-loan")
    public ResponseEntity<LoanApplicationResponse> applyLoan(
            @RequestBody LoanApplicationRequest request, Principal principal) {
        //extract userId from principal (we can customize this with a proper UserDetails implementation)
        Long userId = getUserIdFromPrincipal(principal);
        LoanApplicationResponse response = loanApplicationService.applyLoan(request, userId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/user/loans")
    public ResponseEntity<List<LoanApplicationResponse>> getUserLoanApplications(Principal principal) {
        Long userId = getUserIdFromPrincipal(principal);
        List<LoanApplicationResponse> responses = loanApplicationService.getUserLoanApplications(userId);
        return ResponseEntity.ok(responses);
    }

    // --- Admin APIs --- ----------------------------------------------------------------------------------

    @GetMapping("/admin/loans")
    public ResponseEntity<List<LoanApplicationResponse>> getAllLoanApplications() {
        List<LoanApplicationResponse> responses = loanApplicationService.getAllLoanApplications();
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/admin/loan/{id}")
    public ResponseEntity<LoanApplicationResponse> getLoanApplicationById(@PathVariable Long id) {
        LoanApplicationResponse response = loanApplicationService.getLoanApplicationById(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/admin/loan/{id}/approve")
    public ResponseEntity<LoanApplicationResultResponse> approveLoanApplication(@PathVariable Long id) {
        LoanApplicationResultResponse response = loanApplicationService.approveLoanApplication(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/admin/loan/{id}/reject")
    public ResponseEntity<LoanApplicationResultResponse> rejectLoanApplication(@PathVariable Long id) {
        LoanApplicationResultResponse response= loanApplicationService.rejectLoanApplication(id);
        return ResponseEntity.ok(response);
    }

    // Helper method to extract userId from Principal
    private Long getUserIdFromPrincipal(Principal principal) {
        // This is a placeholder; in a real app, you’d fetch the userId from the principal or JWT claims.
        // For now, let’s parse the name (which might be the username or ID depending on your setup).
        try {
            return Long.parseLong(principal.getName());
        } catch (NumberFormatException e) {
            throw new RuntimeException("Invalid user identifier.");
        }
    }
}
