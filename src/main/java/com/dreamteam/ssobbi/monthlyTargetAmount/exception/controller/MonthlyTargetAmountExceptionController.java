package com.dreamteam.ssobbi.monthlyTargetAmount.exception.controller;

import com.dreamteam.ssobbi.base.exception.NotFoundException;
import com.dreamteam.ssobbi.base.exception.controller.response.ExceptionResponse;
import com.dreamteam.ssobbi.monthlyTargetAmount.exception.DuplicateValueException;
import com.dreamteam.ssobbi.monthlyTargetAmount.exception.NegativeValueException;
import com.dreamteam.ssobbi.monthlyTargetAmount.exception.NullValueException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class MonthlyTargetAmountExceptionController {
    @ExceptionHandler({DuplicateValueException.class, NegativeValueException.class, NullValueException.class})
    public ResponseEntity<ExceptionResponse> handleNotFoundException(Exception e) {
        ExceptionResponse response = ExceptionResponse.builder()
                .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .message(e.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}
