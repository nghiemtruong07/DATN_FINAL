package com.PCThanhCong.exceptions;

@SuppressWarnings("serial")
public class ForbiddenException extends RuntimeException {
	public ForbiddenException(String message) {
		super(message);
	}
}
