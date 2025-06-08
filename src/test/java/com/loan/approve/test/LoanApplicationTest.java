package com.loan.approve.test;

import com.loan.approve.dto.LoanApplicationRequest;
import com.loan.approve.dto.LoanApplicationResponse;
import com.loan.approve.dto.LoanApplicationResultResponse;
import com.loan.approve.resource.LoanApplicationController;
import com.loan.approve.service.service.LoanApplicationService;
import com.loan.approve.util.LoanStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

class LoanApplicationControllerTest {

    private MockMvc mockMvc;

    @Mock
    private LoanApplicationService loanApplicationService;

    @Mock
    private Principal principal;

    @InjectMocks
    private LoanApplicationController loanApplicationController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(loanApplicationController).build();

        when(principal.getName()).thenReturn("123"); // Mock principal to return userId 123
    }

    @Test
    void applyLoan_ShouldReturnLoanApplicationResponse() throws Exception {
        LoanApplicationRequest request = new LoanApplicationRequest();
        request.setAmount(BigDecimal.valueOf(10000.0));
        request.setPurpose("Home renovation");

        LoanApplicationResponse response = new LoanApplicationResponse();
        response.setApplicationId(1L);
        response.setStatus(LoanStatus.PENDING);

        when(loanApplicationService.applyLoan(any(LoanApplicationRequest.class), anyLong()))
                .thenReturn(response);

        mockMvc.perform(post("/loan/user/apply-loan")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"amount\":10000.0,\"purpose\":\"Home renovation\"}")
                        .principal(principal))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.status", is("PENDING")));
    }

    @Test
    void getUserLoanApplications_ShouldReturnListOfLoans() throws Exception {
        LoanApplicationResponse loan1 = new LoanApplicationResponse();
        loan1.setApplicationId(1L);
        loan1.setStatus(LoanStatus.PENDING);

        LoanApplicationResponse loan2 = new LoanApplicationResponse();
        loan2.setApplicationId(2L);
        loan2.setStatus(LoanStatus.APPROVED);

        List<LoanApplicationResponse> loans = Arrays.asList(loan1, loan2);

        when(loanApplicationService.getUserLoanApplications(anyLong()))
                .thenReturn(loans);

        mockMvc.perform(get("/loan/user/loans")
                        .principal(principal))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[1].id", is(2)));
    }

    @Test
    void getAllLoanApplications_ShouldReturnAllLoans() throws Exception {
        LoanApplicationResponse loan1 = new LoanApplicationResponse();
        loan1.setApplicationId(1l);

        LoanApplicationResponse loan2 = new LoanApplicationResponse();
        loan1.setApplicationId(1l);

        List<LoanApplicationResponse> loans = Arrays.asList(loan1, loan2);

        when(loanApplicationService.getAllLoanApplications())
                .thenReturn(loans);

        mockMvc.perform(get("/loan/admin/loans"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    void getLoanApplicationById_ShouldReturnLoanDetails() throws Exception {
        LoanApplicationResponse response = new LoanApplicationResponse();
        response.setApplicationId(1L);
        response.setStatus(LoanStatus.APPROVED);

        when(loanApplicationService.getLoanApplicationById(1L))
                .thenReturn(response);

        mockMvc.perform(get("/loan/admin/loan/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.status", is("PENDING")));
    }

    @Test
    void approveLoanApplication_ShouldReturnSuccessResponse() throws Exception {
        LoanApplicationResultResponse response = new LoanApplicationResultResponse();
        response.setMessage("true");
        response.setMessage("Loan approved successfully");

        when(loanApplicationService.approveLoanApplication(1L))
                .thenReturn(response);

        mockMvc.perform(put("/loan/admin/loan/1/approve"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(jsonPath("$.message", is("Loan approved successfully")));
    }

    @Test
    void rejectLoanApplication_ShouldReturnSuccessResponse() throws Exception {
        LoanApplicationResultResponse response = new LoanApplicationResultResponse();
        response.setMessage("true");
        response.setMessage("Loan rejected successfully");

        when(loanApplicationService.rejectLoanApplication(1L))
                .thenReturn(response);

        mockMvc.perform(put("/loan/admin/loan/1/reject"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(jsonPath("$.message", is("Loan rejected successfully")));
    }

    @Test
    void getUserIdFromPrincipal_WithInvalidUserId_ShouldThrowException() throws Exception {
        when(principal.getName()).thenReturn("invalid");

        mockMvc.perform(get("/loan/user/loans")
                        .principal(principal))
                .andExpect(status().is5xxServerError());
    }
}