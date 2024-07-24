package com.dreamteam.ssobbi.base.exception;

public class UnauthorizedAccessException extends RuntimeException{
    private static final String MESSAGE = "접근 권한이 없습니다.";

    public UnauthorizedAccessException() {
        super(MESSAGE);
    }
}
