package com.loan.approve.dto;

import jakarta.validation.constraints.Size;

public class AdminLoanActionRequest {

    @Size(max = 500)
    private String comment;

    // Getters and setters...
}

