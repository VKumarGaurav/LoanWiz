package com.loan.approve.resource;

import com.loan.approve.dto.DisbursementDTO;
import com.loan.approve.service.service.DisbursementService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.*;
import io.swagger.v3.oas.annotations.media.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/disbursements")
@Slf4j
@Tag(name = "Disbursement Processor", description = "APIs for handling loan disbursements")
public class DisbursementController {

    @Autowired
    private DisbursementService disbursementService;

    @PostMapping
    @Operation(
            summary = "Initiate disbursement",
            description = "Initiates the disbursement process for an approved loan application."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Disbursement initiated successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = DisbursementDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request payload")
    })
    public ResponseEntity<DisbursementDTO> initiateDisbursement(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Disbursement request details",
                    required = true,
                    content = @Content(schema = @Schema(implementation = DisbursementDTO.class))
            )
            @RequestBody DisbursementDTO dto) {
        DisbursementDTO response = disbursementService.initiateDisbursement(dto);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get disbursement status",
            description = "Fetches the status of a specific disbursement."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Disbursement status retrieved",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = DisbursementDTO.class))),
            @ApiResponse(responseCode = "404", description = "Disbursement not found")
    })
    public ResponseEntity<DisbursementDTO> getDisbursementStatus(@PathVariable Long id) {
        DisbursementDTO response = disbursementService.getDisbursementStatus(id);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/status")
    @Operation(
            summary = "Update disbursement status",
            description = "Updates the status of a specific disbursement record."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Disbursement status updated successfully"),
            @ApiResponse(responseCode = "404", description = "Disbursement not found")
    })
    public ResponseEntity<Void> updateDisbursementStatus(
            @PathVariable Long id,
            @RequestParam String status) {
        disbursementService.updateDisbursementStatus(id, status);
        return ResponseEntity.noContent().build();
    }
}

