package com.practice.serviceImpl;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.practice.dtorequest.UserRequest;
import com.practice.dtoresponse.UserResponse;
import com.practice.mapper.UserMapper;
import com.practice.repository.UserRepository;
import com.practice.service.UserServiceInterface;
import com.practice.utility.ResponseStructure;

@Service
public class UserServiceImpl implements UserServiceInterface {

	private final UserRepository userRepository;
	private final UserMapper userMapper;

	public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
		this.userRepository = userRepository;
		this.userMapper = userMapper;
	}

	@Override
	public ResponseEntity<ResponseStructure<UserResponse>> registerUser(UserRequest userRequest) {
					
		return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseStructure<UserResponse>()
				.setStatusCode(HttpStatus.CREATED.value())
				.setMessage("user object created successfully")
				.setData(userMapper.mapToUserResponse(userRepository.save(userMapper.mapToUser(userRequest)))));
		
	}

}
