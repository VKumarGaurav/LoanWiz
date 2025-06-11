package com.loan.approve.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.loan.approve.dto.DisbursementDTO;
import com.loan.approve.resource.DisbursementController;
import com.loan.approve.service.service.DisbursementService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class DisbursementControllerTest {

    private MockMvc mockMvc;

    @Mock
    private DisbursementService disbursementService;

    @InjectMocks
    private DisbursementController disbursementController;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(disbursementController).build();
    }

    @Test
    void testInitiateDisbursement() throws Exception {
        DisbursementDTO dto = new DisbursementDTO(1L, 123L, "IN_PROGRESS");

        when(disbursementService.initiateDisbursement(any(DisbursementDTO.class))).thenReturn(dto);

        mockMvc.perform(post("/api/disbursements")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("IN_PROGRESS"));
    }

    @Test
    void testGetDisbursementStatus() throws Exception {
        DisbursementDTO dto = new DisbursementDTO(1L, 123L, "COMPLETED");

        when(disbursementService.getDisbursementStatus(1L)).thenReturn(dto);

        mockMvc.perform(get("/api/disbursements/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("COMPLETED"));
    }

    @Test
    void testUpdateDisbursementStatus() throws Exception {
        doNothing().when(disbursementService).updateDisbursementStatus(1L, "CANCELLED");

        mockMvc.perform(patch("/api/disbursements/1/status")
                        .param("status", "CANCELLED"))
                .andExpect(status().isNoContent());

        verify(disbursementService, times(1)).updateDisbursementStatus(1L, "CANCELLED");
    }
}

