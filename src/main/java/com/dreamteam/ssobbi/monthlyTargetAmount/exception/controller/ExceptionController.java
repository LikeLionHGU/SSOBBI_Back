package com.dreamteam.ssobbi.monthlyTargetAmount.exception.controller;

import com.dreamteam.ssobbi.base.exception.NotFoundException;
import com.dreamteam.ssobbi.monthlyTargetAmount.exception.DuplicateValueException;
import com.dreamteam.ssobbi.monthlyTargetAmount.exception.NegativeValueException;
import com.dreamteam.ssobbi.monthlyTargetAmount.exception.NullValueException;
import com.dreamteam.ssobbi.monthlyTargetAmount.exception.controller.response.ExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionController {
    @ExceptionHandler({NullValueException.class, DuplicateValueException.class, NegativeValueException.class})
    public ResponseEntity<ExceptionResponse> handleNotFoundException(NotFoundException e) {
        ExceptionResponse response = ExceptionResponse.builder()
                .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .message(e.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}
