package com.loan.approve.resource;


import com.loan.approve.dto.ApprovalRequestDTO;
import com.loan.approve.dto.ApprovalResponseDTO;
import com.loan.approve.service.service.ApprovalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/approvals")
@Tag(name = "Approval Workflow", description = "Manage loan approvals and screening process")
public class ApprovalController {

    @Autowired
    private ApprovalService approvalService;

    @PostMapping
    @Operation(
            summary = "Submit an approval decision",
            description = "Allows a loan officer to submit an approval decision for a loan application."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Approval submitted successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApprovalResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request payload"),
            @ApiResponse(responseCode = "404", description = "Loan or approver not found")
    })
    public ResponseEntity<ApprovalResponseDTO> submitApproval(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Approval request details",
                    required = true,
                    content = @Content(schema = @Schema(implementation = ApprovalRequestDTO.class))
            )
            @RequestBody ApprovalRequestDTO request) {
        ApprovalResponseDTO response = approvalService.submitApproval(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/loan/{loanId}")
    @Operation(
            summary = "Get all approvals by loan ID",
            description = "Fetches all approval records for a given loan, ordered by approval level."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Approvals retrieved successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApprovalResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Loan not found")
    })
    public ResponseEntity<List<ApprovalResponseDTO>> getApprovalsByLoan(
            @Parameter(description = "Loan ID to fetch approvals for", required = true)
            @PathVariable Long loanId) {
        List<ApprovalResponseDTO> approvals = approvalService.getApprovalsByLoanId(loanId);
        return ResponseEntity.ok(approvals);
    }

    @PostMapping("/loan/{loanId}/screening")
    @Operation(
            summary = "Perform automated initial screening",
            description = "Performs automated initial risk screening for a loan application using business rules."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Screening completed successfully"),
            @ApiResponse(responseCode = "404", description = "Loan not found")
    })
    public ResponseEntity<Void> performInitialScreening(
            @Parameter(description = "Loan ID to perform screening on", required = true)
            @PathVariable Long loanId) {
        approvalService.performInitialScreening(loanId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/pending/{approverId}")
    @Operation(
            summary = "Get pending approvals for a specific approver",
            description = "Fetches all pending approvals assigned to the given approver (loan officer)."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pending approvals retrieved successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApprovalResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Approver not found")
    })
    public ResponseEntity<List<ApprovalResponseDTO>> getPendingApprovals(
            @Parameter(description = "Approver ID to fetch pending approvals for", required = true)
            @PathVariable Long approverId) {
        List<ApprovalResponseDTO> approvals = approvalService.getPendingApprovalsForUser(approverId);
        return ResponseEntity.ok(approvals);
    }
}
