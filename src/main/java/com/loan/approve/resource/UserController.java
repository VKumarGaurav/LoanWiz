package com.loan.approve.resource;

import com.loan.approve.dto.LoanApplicationRequest;
import com.loan.approve.dto.LoanApplicationResponse;
import com.loan.approve.dto.UserRegistrationRequest;
import com.loan.approve.dto.UserResponse;
import com.loan.approve.exception.handlers.RecordNotFoundException;
import com.loan.approve.service.services.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping(path = "/user/")
public class UserController {

    @Autowired private UserService userService;

    // Register a new user
    @PostMapping("/register")
    public ResponseEntity<UserResponse> registerUser(
            @Valid  @RequestBody UserRegistrationRequest request) {
        UserResponse response = userService.registerUser(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // Get user by ID
    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long userId) {
            UserResponse response = userService.getUserById(userId);
            return ResponseEntity.ok(response);

    }

    // Get all loan applications for a user
    @GetMapping("/{userId}/loans")
    public ResponseEntity<List<LoanApplicationResponse>> getUserLoanApplications(
            @PathVariable Long userId) {
        List<LoanApplicationResponse> responses = userService.getUserLoanApplications(userId);
        return ResponseEntity.ok(responses);
    }
    // Partial update user details
    @PatchMapping("/{userId}")
    public ResponseEntity<UserResponse> partialUpdateUser(
            @PathVariable Long userId,
            @RequestBody Map<String, Object> updates) {
            UserResponse updatedUser = userService.partialUpdateUser(userId, updates);
            return ResponseEntity.ok(updatedUser);

    }

    // Deactivate user account
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deactivateUser(@PathVariable Long userId) {
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








