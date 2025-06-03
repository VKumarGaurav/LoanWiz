package com.loan.approve.resource;

import com.loan.approve.dto.LoanApplicationRequest;
import com.loan.approve.dto.LoanApplicationResponse;
import com.loan.approve.service.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping(path = "/user/")
public class UserController {

    @Autowired private UserService userService;

    @GetMapping(path = "listOfloan/userId/{userId}")
    public ResponseEntity<List<LoanApplicationResponse>> getUserLoanApplications
            (@PathVariable("userId") Long userId){

        return new ResponseEntity<>(
                userService.getUserLoanApplications(userId),
                HttpStatus.FOUND
        );
    }

}





















