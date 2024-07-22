package com.practice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.practice.dtorequest.UserRequest;
import com.practice.dtoresponse.UserResponse;
import com.practice.serviceImpl.UserServiceImpl;
import com.practice.utility.ResponseStructure;

@RequestMapping("/users")
@RestController
public class UserController {
	
	private final UserServiceImpl userServiceImpl;
	
	public UserController(UserServiceImpl userServiceImpl) {
		this.userServiceImpl = userServiceImpl;
	}

	@PostMapping
	public ResponseEntity<ResponseStructure<UserResponse>> registerUser( @RequestBody UserRequest userRequest){
		
		return userServiceImpl.registerUser(userRequest);
		
	}
	
}
