package com.loan.approve.test;

import com.loan.approve.dto.AmortizationScheduleDTO;
import com.loan.approve.resource.AmortizationScheduleController;
import com.loan.approve.service.service.AmortizationScheduleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class AmortizationScheduleControllerTest {

    private MockMvc mockMvc;

    @Mock
    private AmortizationScheduleService amortizationScheduleService;

    @InjectMocks
    private AmortizationScheduleController amortizationScheduleController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(amortizationScheduleController).build();
    }

    @Test
    void testCalculateAmortizationSchedule() throws Exception {
        AmortizationScheduleDTO dto = new AmortizationScheduleDTO(1, LocalDate.now(), new BigDecimal("1000.00"), new BigDecimal("100.00"), new BigDecimal("900.00"));
        when(amortizationScheduleService.calculateAmortizationSchedule(any(), anyDouble(), anyInt(), any(LocalDate.class)))
                .thenReturn(Collections.singletonList(dto));

        mockMvc.perform(get("/api/amortization/calculate")
                        .param("principal", "10000")
                        .param("annualInterestRate", "5.0")
                        .param("termInMonths", "12")
                        .param("startDate", "2025-06-11"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].paymentNumber").value(1));
    }
}

