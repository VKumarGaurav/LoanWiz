package com.loan.approve.resource;

import com.loan.approve.dto.LoanProductDTO;
import com.loan.approve.service.service.LoanProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api/loan-products")
@Tag(name = "Loan Product Catalog", description = "APIs for managing loan products and interest rates")
public class LoanProductController {
    @Autowired
    private LoanProductService loanProductService;

    @PostMapping
    @Operation(
            summary = "Create a new loan product",
            description = "Allows admin to add a new loan product to the catalog."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Loan product created successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = LoanProductDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request payload")
    })
    public ResponseEntity<LoanProductDTO> createLoanProduct(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Loan product details",
                    required = true,
                    content = @Content(schema = @Schema(implementation = LoanProductDTO.class))
            )
            @RequestBody LoanProductDTO dto) {
        LoanProductDTO response = loanProductService.createLoanProduct(dto);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get loan product by ID",
            description = "Fetches details of a specific loan product."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Loan product found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = LoanProductDTO.class))),
            @ApiResponse(responseCode = "404", description = "Loan product not found")
    })
    public ResponseEntity<LoanProductDTO> getLoanProduct(@PathVariable Long id) {
        LoanProductDTO response = loanProductService.getLoanProductById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    @Operation(
            summary = "Get all loan products",
            description = "Retrieves a list of all loan products."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Loan products retrieved successfully",
                    content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = LoanProductDTO.class))))
    })
    public ResponseEntity<List<LoanProductDTO>> getAllLoanProducts() {
        List<LoanProductDTO> response = loanProductService.getAllLoanProducts();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Update loan product",
            description = "Updates the details of an existing loan product."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Loan product updated successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = LoanProductDTO.class))),
            @ApiResponse(responseCode = "404", description = "Loan product not found")
    })
    public ResponseEntity<LoanProductDTO> updateLoanProduct(
            @PathVariable Long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Updated loan product details",
                    required = true,
                    content = @Content(schema = @Schema(implementation = LoanProductDTO.class))
            )
            @RequestBody LoanProductDTO dto) {
        LoanProductDTO response = loanProductService.updateLoanProduct(id, dto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete loan product",
            description = "Deletes a loan product by ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Loan product deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Loan product not found")
    })
    public ResponseEntity<Void> deleteLoanProduct(@PathVariable Long id) {
        loanProductService.deleteLoanProduct(id);
        return ResponseEntity.noContent().build();
    }
}