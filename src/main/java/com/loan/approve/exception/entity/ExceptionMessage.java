package com.loan.approve.exception.entity;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExceptionMessage {
    private LocalDateTime TIMESTAMP;
    private String MESSAGE;
    private String DETAILS;
}