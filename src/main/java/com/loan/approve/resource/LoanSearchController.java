package com.loan.approve.resource;

import com.loan.approve.entity.LoanApplicationDocument;
import com.loan.approve.service.services.LoanSearchService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/loans-search")
@Tag(name = "Loan Search API", description = "API for managing and searching loan search")
public class LoanSearchController {

    @Autowired
    private LoanSearchService searchService;

    @PostMapping("/index")
    @Operation(
            summary = "Index a loan application",
            description = "Creates or updates a loan application document in the search index"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully indexed the loan application",
                    content = @Content(schema = @Schema(implementation = LoanApplicationDocument.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input provided"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public LoanApplicationDocument indexLoan(
            @Parameter(description = "Loan application document to be indexed", required = true)
            @RequestBody LoanApplicationDocument doc) {
        return searchService.save(doc);
    }

    @GetMapping
    @Operation(
            summary = "Search loan applications",
            description = "Search for loan applications by applicant name"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved matching loan applications",
                    content = @Content(schema = @Schema(implementation = LoanApplicationDocument[].class))),
            @ApiResponse(responseCode = "400", description = "Invalid search parameter"),
            @ApiResponse(responseCode = "404", description = "No loans found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public List<LoanApplicationDocument> searchLoans(
            @Parameter(description = "Applicant name to search for", required = true, example = "John Doe")
            @RequestParam String name) {
        return searchService.searchByApplicantName(name);
    }
}
