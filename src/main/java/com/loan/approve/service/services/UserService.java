package com.loan.approve.service.services;

import com.loan.approve.dto.LoanApplicationRequest;
import com.loan.approve.dto.LoanApplicationResponse;
import com.loan.approve.dto.UserRegistrationRequest;
import com.loan.approve.dto.UserResponse;

import java.util.List;
import java.util.Map;

public interface UserService {

    List<LoanApplicationResponse> getUserLoanApplications(Long userId);
    UserResponse registerUser(UserRegistrationRequest request);
    UserResponse getUserById(Long userId);
    UserResponse partialUpdateUser(Long userId, Map<String, Object> updates);
    void deactivateUser(Long userId);
}
