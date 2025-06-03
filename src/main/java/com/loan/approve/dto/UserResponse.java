package com.loan.approve.dto;

import lombok.*;

import java.time.Instant;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserResponse {
    private Long id;
    private String username;
    private String email;
    private String fullName;
    private String role; // Optional: "USER" or "ADMIN"
    private Instant createdAt;
    private Instant updatedAt;
}
