package com.practice.exception;

public class CarNotFoundByIdException extends RuntimeException {

	private String message;

	public CarNotFoundByIdException(String message) {
		super();
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	
	
}
