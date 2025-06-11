package com.loan.approve.resource;

import com.loan.approve.dto.AmortizationScheduleDTO;
import com.loan.approve.service.service.AmortizationScheduleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.*;
import io.swagger.v3.oas.annotations.media.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api/amortization")
@Tag(name = "Amortization Calculator", description = "APIs for calculating amortization schedules")
public class AmortizationScheduleController {
    @Autowired
    private AmortizationScheduleService amortizationScheduleService;

    @GetMapping("/calculate")
    @Operation(
            summary = "Calculate amortization schedule",
            description = "Calculates the amortization schedule based on principal, interest rate, term, and start date."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Amortization schedule calculated successfully",
                    content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = AmortizationScheduleDTO.class)))),
            @ApiResponse(responseCode = "400", description = "Invalid input parameters")
    })
    public ResponseEntity<List<AmortizationScheduleDTO>> calculateSchedule(
            @RequestParam BigDecimal principal,
            @RequestParam Double annualInterestRate,
            @RequestParam Integer termInMonths,
            @RequestParam String startDate
    ) {
        List<AmortizationScheduleDTO> schedule = amortizationScheduleService
                .calculateAmortizationSchedule(principal, annualInterestRate, termInMonths, LocalDate.parse(startDate));
        return ResponseEntity.ok(schedule);
    }
}

