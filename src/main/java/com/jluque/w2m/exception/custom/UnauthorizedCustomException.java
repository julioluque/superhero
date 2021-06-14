package com.jluque.w2m.exception.custom;

public class UnauthorizedCustomException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private static final String DESCRIPTION = "Unauthorized Exception";

	public UnauthorizedCustomException(String detail) {
		super(DESCRIPTION + ". " + detail);
	}

}
