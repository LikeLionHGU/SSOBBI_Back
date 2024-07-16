package com.dreamteam.ssobbi.auth.exception;

public class FailedToGoogleLoginException extends RuntimeException{
    private static final String MESSAGE = "구글 로그인에 실패했습니다.";

    public FailedToGoogleLoginException() {
        super(MESSAGE);
    }
}
