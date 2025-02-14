package com.practice.exception;

public class ServiceNotFoundInCartException extends RuntimeException{

	
	private String message;

	public ServiceNotFoundInCartException(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

}
