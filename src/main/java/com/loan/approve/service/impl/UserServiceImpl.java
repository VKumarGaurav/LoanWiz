package com.loan.approve.service.impl;

import com.loan.approve.dto.LoanApplicationResponse;
import com.loan.approve.dto.UserRegistrationRequest;
import com.loan.approve.dto.UserResponse;
import com.loan.approve.entity.LoanApplication;
import com.loan.approve.entity.User;
import com.loan.approve.repository.LoanApplicationRepository;
import com.loan.approve.repository.UserRepository;
import com.loan.approve.service.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired private UserRepository userRepository;
    @Autowired private LoanApplicationRepository loanApplicationRepository;

    @Override
    public List<LoanApplicationResponse> getUserLoanApplications(Long userId) {
        // Fetch all loan applications for this user from the repository.
        List<LoanApplication> applications = loanApplicationRepository.findByUserId(userId);

        // Map each LoanApplication entity to a LoanApplicationResponse DTO.
        return applications.stream()
                .map(application -> {
                    LoanApplicationResponse response = new LoanApplicationResponse();
                    response.setApplicationId(application.getId());
                    response.setAmount(application.getAmount());
                    response.setPurpose(application.getPurpose());
                    response.setAnnualIncome(application.getAnnualIncome());
                    response.setEmployment(application.getEmployment());
                    response.setCollateral(application.getCollateral());
                    response.setStatus(application.getStatus());
                    response.setCreatedAt(application.getCreatedAt());
                    response.setUpdatedAt(application.getUpdatedAt());
                    return response;
                })
                .collect(Collectors.toList());
    }

    @Override
    public UserResponse registerUser(UserRegistrationRequest request) {
        // Check if the user already exists
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("User with this email already exists.");
        }

        //  Create a new User entity
        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        //user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setPassword((request.getPassword()));
        user.setRole("USER");

        // Save the user
        User savedUser = userRepository.save(user);

        //Convert to UserResponse
        return mapToUserResponse(savedUser);
    }

    @Override
    public UserResponse getUserById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        return mapToUserResponse(user);
    }

//    private Long id;
//    private String username;
//    private String email;
//    private String fullName;
//    private String role; // Optional: "USER" or "ADMIN"
//    private Instant createdAt;
//    private Instant updatedAt;

    //Helper method to convert User entity to UserResponse DTO
    private UserResponse mapToUserResponse(User user) {
        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setUsername(user.getUsername());
        response.setEmail(user.getEmail());
        response.setRole(user.getRole());
        return response;
    }

}











