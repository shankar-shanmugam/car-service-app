package com.practice.service;

import org.springframework.http.ResponseEntity;

import com.practice.dtorequest.AuthRequest;
import com.practice.dtorequest.UserRequest;
import com.practice.dtoresponse.UserResponse;
import com.practice.utility.ResponseStructure;

import java.util.List;

public interface UserServiceInterface {
	
    ResponseEntity<ResponseStructure<UserResponse>> registerUser(UserRequest userRequest);
    
    ResponseEntity<ResponseStructure<UserResponse>> updateUser(UserRequest userRequest);
    
    ResponseEntity<ResponseStructure<UserResponse>> deleteUser(int id);
    
    ResponseEntity<ResponseStructure<UserResponse>> getUserById(int id);
    
    ResponseEntity<ResponseStructure<List<UserResponse>>> getAllUsers();
    
    ResponseEntity<ResponseStructure<String>>login(AuthRequest authRequest);
}
