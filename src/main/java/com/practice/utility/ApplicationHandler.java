package com.practice.utility;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.practice.exception.CarNotFoundByIdException;
import com.practice.exception.ServiceNotFoundByIdException;
import com.practice.exception.UserNotFoundByIdException;

@RestControllerAdvice
public class ApplicationHandler {

	
	@ExceptionHandler
	public ResponseEntity<ErrorStructure<String>> carNotFoundByIdException(CarNotFoundByIdException ex){
		
		ErrorStructure<String>er= new ErrorStructure<>();
		er.setErrorstatus(HttpStatus.NOT_FOUND.value());
		er.setErrordata("car with the requested id is not prsent in the database");
		er.setErrormessage(ex.getMessage());
		return new ResponseEntity<ErrorStructure<String>>(er,HttpStatus.NOT_FOUND);
		
	}
	
	@ExceptionHandler
	public ResponseEntity<ErrorStructure<String>> serviceNotFoundByIdException(ServiceNotFoundByIdException ex){
		
		ErrorStructure<String>er= new ErrorStructure<>();
		er.setErrorstatus(HttpStatus.NOT_FOUND.value());
		er.setErrordata("service with the requested id is not prsent in the database");
		er.setErrormessage(ex.getMessage());
		return new ResponseEntity<ErrorStructure<String>>(er,HttpStatus.NOT_FOUND);	
	}

	@ExceptionHandler
	public ResponseEntity<ErrorStructure<String>> userNotFoundByIdException(UserNotFoundByIdException ex){
		
		ErrorStructure<String>er= new ErrorStructure<>();
		er.setErrorstatus(HttpStatus.NOT_FOUND.value());
		er.setErrordata("user with the requested id is not prsent in the database");
		er.setErrormessage(ex.getMessage());
		return new ResponseEntity<ErrorStructure<String>>(er,HttpStatus.NOT_FOUND);
		
	}
	
}
