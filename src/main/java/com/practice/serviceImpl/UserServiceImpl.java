package com.practice.serviceImpl;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.practice.dtorequest.UserRequest;
import com.practice.dtoresponse.UserResponse;
import com.practice.entity.User;
import com.practice.exception.UserNotFoundByIdException;
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

	@Override
	public ResponseEntity<ResponseStructure<UserResponse>> updateUser(UserRequest userRequest) {
		
		String name = SecurityContextHolder.getContext().getAuthentication().getName();
		
		return userRepository.findByEmail(name).map(existingUser -> {
			User user = userMapper.mapToUser(userRequest);
			user.setId(existingUser.getId());
			user = userRepository.save(user);
			return ResponseEntity.status(HttpStatus.OK)
					.body(new ResponseStructure<UserResponse>().setStatusCode(HttpStatus.OK.value())
							.setMessage("User updated successfully").setData( userMapper.mapToUserResponse(user)));
		}).orElseThrow(() -> new UserNotFoundByIdException("User ID not found"));
	}

	@Override
	public ResponseEntity<ResponseStructure<UserResponse>> deleteUser(int id) {
		return userRepository.findById(id).map(user -> {
			userRepository.delete(user);
			return ResponseEntity.status(HttpStatus.OK)
					.body(new ResponseStructure<UserResponse>()
							.setStatusCode(HttpStatus.OK.value())
							.setMessage("User deleted successfully")
							.setData(userMapper.mapToUserResponse(user)));
		}).orElseThrow(() -> new UserNotFoundByIdException("User ID not found"));
	}

	@Override
	public ResponseEntity<ResponseStructure<UserResponse>> getUserById(int id) {
		return userRepository.findById(id)
				.map(user -> ResponseEntity.status(HttpStatus.FOUND)
						.body(new ResponseStructure<UserResponse>()
								.setStatusCode(HttpStatus.FOUND.value())
								.setMessage("User found successfully")
								.setData(userMapper.mapToUserResponse(user))))
				
				.orElseThrow(() -> new UserNotFoundByIdException("User ID not found"));
	}

	@Override
	public ResponseEntity<ResponseStructure<List<UserResponse>>> getAllUsers() {
		
		List<UserResponse> users = userRepository.findAll().stream().map(userMapper::mapToUserResponse).toList();
		
		return ResponseEntity.status(HttpStatus.FOUND)
				.body(new ResponseStructure<List<UserResponse>>()
				.setStatusCode(HttpStatus.FOUND.value())
				.setMessage("List of users fetched successfully").setData(users));
	}
}
