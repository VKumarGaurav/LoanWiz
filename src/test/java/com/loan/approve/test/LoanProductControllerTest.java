package com.loan.approve.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.loan.approve.dto.LoanProductDTO;
import com.loan.approve.resource.LoanProductController;
import com.loan.approve.service.service.LoanProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class LoanProductControllerTest {

    private MockMvc mockMvc;

    @Mock
    private LoanProductService loanProductService;

    @InjectMocks
    private LoanProductController loanProductController;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(loanProductController).build();
    }

    @Test
    void testCreateLoanProduct() throws Exception {
        LoanProductDTO dto = new LoanProductDTO(1L, "Home Loan", 8.5, 20);

        when(loanProductService.createLoanProduct(any(LoanProductDTO.class))).thenReturn(dto);

        mockMvc.perform(post("/api/loan-products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Home Loan"))
                .andExpect(jsonPath("$.interestRate").value(8.5));

        verify(loanProductService, times(1)).createLoanProduct(any(LoanProductDTO.class));
    }

    @Test
    void testGetAllLoanProducts() throws Exception {
        LoanProductDTO dto = new LoanProductDTO(1L, "Car Loan", 7.0, 5);
        when(loanProductService.getAllLoanProducts()).thenReturn(Collections.singletonList(dto));

        mockMvc.perform(get("/api/loan-products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Car Loan"));
    }

    @Test
    void testGetLoanProductById() throws Exception {
        LoanProductDTO dto = new LoanProductDTO(1L, "Personal Loan", 10.0, 3);
        when(loanProductService.getLoanProductById(1L)).thenReturn(dto);

        mockMvc.perform(get("/api/loan-products/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Personal Loan"));
    }

    @Test
    void testDeleteLoanProduct() throws Exception {
        doNothing().when(loanProductService).deleteLoanProduct(1L);

        mockMvc.perform(delete("/api/loan-products/1"))
                .andExpect(status().isNoContent());

        verify(loanProductService, times(1)).deleteLoanProduct(1L);
    }
}

