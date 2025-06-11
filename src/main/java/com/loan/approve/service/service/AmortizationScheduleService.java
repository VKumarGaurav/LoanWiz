package com.loan.approve.service.service;

import com.loan.approve.dto.AmortizationScheduleDTO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface AmortizationScheduleService {

    List<AmortizationScheduleDTO> calculateAmortizationSchedule(
            BigDecimal principalAmount,
            Double annualInterestRate,
            Integer termInMonths,
            LocalDate startDate
    );
}