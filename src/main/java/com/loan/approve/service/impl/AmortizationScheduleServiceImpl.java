package com.loan.approve.service.impl;

import com.loan.approve.dto.AmortizationScheduleDTO;
import com.loan.approve.service.service.AmortizationScheduleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class AmortizationScheduleServiceImpl implements AmortizationScheduleService {

    @Override
    public List<AmortizationScheduleDTO> calculateAmortizationSchedule(
            BigDecimal principal,
            Double annualRate,
            Integer termMonths,
            LocalDate startDate
    ) {
        List<AmortizationScheduleDTO> schedule = new ArrayList<>();
        BigDecimal monthlyRate = BigDecimal.valueOf(annualRate / 12 / 100);
        BigDecimal monthlyPayment = principal.multiply(monthlyRate)
                .divide(BigDecimal.ONE.subtract(
                                BigDecimal.ONE.divide((BigDecimal.ONE.add(monthlyRate)).pow(termMonths), 10, RoundingMode.HALF_UP)),
                        2, RoundingMode.HALF_UP);

        BigDecimal balance = principal;
        for (int i = 1; i <= termMonths; i++) {
            BigDecimal interest = balance.multiply(monthlyRate).setScale(2, RoundingMode.HALF_UP);
            BigDecimal principalComponent = monthlyPayment.subtract(interest);
            balance = balance.subtract(principalComponent);

            schedule.add(AmortizationScheduleDTO.builder()
                    .installmentNumber(i)
                    .paymentDate(startDate.plusMonths(i))
                    .principalComponent(principalComponent)
                    .interestComponent(interest)
                    .remainingBalance(balance.max(BigDecimal.ZERO))
                    .build());
        }

        return schedule;
    }
}

