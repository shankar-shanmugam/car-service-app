package com.practice.exception;

public class ServiceNotFoundByIdException extends RuntimeException {

	private String message;

	public ServiceNotFoundByIdException(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	
	
}
