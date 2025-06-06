package com.loan.approve.resource;

import com.loan.approve.dto.LoanApplicationResponse;
import com.loan.approve.dto.UserRegistrationRequest;
import com.loan.approve.dto.UserResponse;
import com.loan.approve.service.services.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping(path = "/user/")
public class UserController {

    @Autowired private UserService userService;

    @Operation(summary = "Register a new user", description = "Creates a new user account with the provided details.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User registered successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PostMapping("/register")
    public ResponseEntity<UserResponse> registerUser(
            @Valid @RequestBody UserRegistrationRequest request) {
        UserResponse response = userService.registerUser(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Operation(summary = "Get user by ID", description = "Retrieve user details by their unique ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User details retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> getUserById(
            @Parameter(description = "ID of the user to retrieve") @PathVariable Long userId) {
        UserResponse response = userService.getUserById(userId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get all loan applications for a user", description = "Fetch all loan applications associated with a specific user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Loan applications retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @GetMapping("/{userId}/loans")
    public ResponseEntity<List<LoanApplicationResponse>> getUserLoanApplications(
            @Parameter(description = "ID of the user whose loan applications to retrieve") @PathVariable Long userId) {
        List<LoanApplicationResponse> responses = userService.getUserLoanApplications(userId);
        return ResponseEntity.ok(responses);
    }

    @Operation(summary = "Partially update user details", description = "Update specific fields of a user's details using partial update.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User details updated successfully"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @PatchMapping("/{userId}")
    public ResponseEntity<UserResponse> partialUpdateUser(
            @Parameter(description = "ID of the user to update") @PathVariable Long userId,
            @RequestBody Map<String, Object> updates) {
        UserResponse updatedUser = userService.partialUpdateUser(userId, updates);
        return ResponseEntity.ok(updatedUser);
    }

    @Operation(summary = "Deactivate user account", description = "Deactivate (soft delete) a user's account by ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "User account deactivated successfully"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deactivateUser(
            @Parameter(description = "ID of the user to deactivate") @PathVariable Long userId) {
        userService.deactivateUser(userId);
        return ResponseEntity.noContent().build();
    }

}











//Add Swagger Documentation:
//
//@Operation(summary = "Register a new user")
//@ApiResponses(value = {
//        @ApiResponse(responseCode = "201", description = "User created"),
//        @ApiResponse(responseCode = "409", description = "Email already exists")
//})
//@PostMapping("/register")
// ...








