package com.dreamteam.ssobbi.monthlyTargetAmount.exception;

public class NegativeValueException extends RuntimeException {
	public NegativeValueException(String message) {
		super(message);
	}
}
