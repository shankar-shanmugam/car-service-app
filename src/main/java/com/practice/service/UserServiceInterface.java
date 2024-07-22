package com.practice.service;

import org.springframework.http.ResponseEntity;

import com.practice.dtorequest.UserRequest;
import com.practice.dtoresponse.UserResponse;
import com.practice.utility.ResponseStructure;

public interface UserServiceInterface {

	ResponseEntity<ResponseStructure<UserResponse>> registerUser(UserRequest userRequest);
}
