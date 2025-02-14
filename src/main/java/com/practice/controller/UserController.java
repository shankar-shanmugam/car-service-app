package com.practice.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.practice.dtorequest.AuthRequest;
import com.practice.dtorequest.UserRequest;
import com.practice.dtoresponse.UserResponse;
import com.practice.serviceImpl.UserServiceImpl;
import com.practice.utility.ResponseStructure;


@RestController
public class UserController {
	
	private final UserServiceImpl userServiceImpl;
	
	public UserController(UserServiceImpl userServiceImpl) {
		this.userServiceImpl = userServiceImpl;
		
	}

	@PostMapping("/user-login")
	public ResponseEntity<ResponseStructure<String>> login(@RequestBody AuthRequest authRequest) {
		
		return userServiceImpl.login(authRequest);
	}
	
	@PostMapping("/register")
	public ResponseEntity<ResponseStructure<UserResponse>> registerUser( @RequestBody UserRequest userRequest){
		 System.out.println("UserRole in Request: " + userRequest.getUserRole());
		return userServiceImpl.registerUser(userRequest);
		
	}

	@PutMapping("/{id}")
	public ResponseEntity<ResponseStructure<UserResponse>> updateUser(@RequestBody UserRequest userRequest) {
		return userServiceImpl.updateUser(userRequest);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<ResponseStructure<UserResponse>> deleteUser(@PathVariable int id) {
		return userServiceImpl.deleteUser(id);
	}

	@GetMapping("/{id}")
	public ResponseEntity<ResponseStructure<UserResponse>> findUserById(@PathVariable int id) {
		return userServiceImpl.getUserById(id);
	}

	@GetMapping
	public ResponseEntity<ResponseStructure<List<UserResponse>>> findAllUsers() {
		return userServiceImpl.getAllUsers();
	}
}
