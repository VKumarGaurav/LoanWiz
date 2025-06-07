package com.loan.approve.resource;


import com.loan.approve.dto.LoanApplicationRequest;
import com.loan.approve.dto.LoanApplicationResponse;
import com.loan.approve.dto.LoanApplicationResultResponse;
import com.loan.approve.service.services.LoanApplicationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.security.Principal;
import java.util.List;

@RestController
@Slf4j
@RequestMapping(path = "api/loan/")
@Tag(name = "Loan Application API", description = "API for managing and searching loan applications")
public class LoanApplicationController {

    @Autowired private LoanApplicationService loanApplicationService;

    // --- User APIs ----------------------------------------------------------------------------------

    @Operation(summary = "Apply for a loan", description = "Allows a user to submit a new loan application.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Loan application submitted successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @PostMapping("/user/apply-loan")
    public ResponseEntity<LoanApplicationResponse> applyLoan(
            @RequestBody LoanApplicationRequest request,
            Principal principal) {
        Long userId = getUserIdFromPrincipal(principal);
        LoanApplicationResponse response = loanApplicationService.applyLoan(request, userId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get user's loan applications", description = "Fetches all loan applications for the currently authenticated user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Loan applications retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @GetMapping("/user/loans")
    public ResponseEntity<List<LoanApplicationResponse>> getUserLoanApplications(Principal principal) {
        Long userId = getUserIdFromPrincipal(principal);
        List<LoanApplicationResponse> responses = loanApplicationService.getUserLoanApplications(userId);
        return ResponseEntity.ok(responses);
    }

    // --- Admin APIs ----------------------------------------------------------------------------------

    @Operation(summary = "Get all loan applications", description = "Fetches all loan applications in the system (Admin only).")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All loan applications retrieved successfully"),
            @ApiResponse(responseCode = "403", description = "Forbidden: Admin access required")
    })
    @GetMapping("/admin/loans")
    public ResponseEntity<List<LoanApplicationResponse>> getAllLoanApplications() {
        List<LoanApplicationResponse> responses = loanApplicationService.getAllLoanApplications();
        return ResponseEntity.ok(responses);
    }

    @Operation(summary = "Get loan application by ID", description = "Fetches a specific loan application by its ID (Admin only).")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Loan application retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Loan application not found"),
            @ApiResponse(responseCode = "403", description = "Forbidden: Admin access required")
    })
    @GetMapping("/admin/loan/{id}")
    public ResponseEntity<LoanApplicationResponse> getLoanApplicationById(
            @Parameter(description = "ID of the loan application to retrieve") @PathVariable Long id) {
        LoanApplicationResponse response = loanApplicationService.getLoanApplicationById(id);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Approve a loan application", description = "Approves a specific loan application by its ID (Admin only).")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Loan application approved successfully"),
            @ApiResponse(responseCode = "404", description = "Loan application not found"),
            @ApiResponse(responseCode = "403", description = "Forbidden: Admin access required")
    })
    @PutMapping("/admin/loan/{id}/approve")
    public ResponseEntity<LoanApplicationResultResponse> approveLoanApplication(
            @Parameter(description = "ID of the loan application to approve") @PathVariable Long id) {
        LoanApplicationResultResponse response = loanApplicationService.approveLoanApplication(id);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Reject a loan application", description = "Rejects a specific loan application by its ID (Admin only).")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Loan application rejected successfully"),
            @ApiResponse(responseCode = "404", description = "Loan application not found"),
            @ApiResponse(responseCode = "403", description = "Forbidden: Admin access required")
    })
    @PutMapping("/admin/loan/{id}/reject")
    public ResponseEntity<LoanApplicationResultResponse> rejectLoanApplication(
            @Parameter(description = "ID of the loan application to reject") @PathVariable Long id) {
        LoanApplicationResultResponse response = loanApplicationService.rejectLoanApplication(id);
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
