package com.loan.approve.dto;

import lombok.*;

import java.time.LocalDate;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CreditBureauRequest {
    private String ssn;
    private String fullName;
    private LocalDate dateOfBirth;

}
