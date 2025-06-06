package com.loan.approve.exception.handlers;

import com.loan.approve.exception.entity.ExceptionMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandlers {

    @ExceptionHandler(RecordNotFoundException.class)
    public ResponseEntity<ExceptionMessage> recordNotFoundExceptionHandler(
            RecordNotFoundException exception,
            WebRequest request
    ){
        ExceptionMessage message = ExceptionMessage.builder()
                .TIMESTAMP(LocalDateTime.now())
                .MESSAGE(exception.getMessage())
                .DETAILS(request.getDescription(false) )
                .build();

        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }
}
