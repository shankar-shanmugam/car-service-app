package com.practice.mapper;

import org.springframework.stereotype.Component;

import com.practice.dtorequest.UserRequest;
import com.practice.dtoresponse.UserResponse;
import com.practice.entity.User;

@Component
public class UserMapper {

	
	public User mapToUser(UserRequest userRequest) {
		
		
		User user= new User();
		user.setName(userRequest.getName());
		user.setEmail(userRequest.getEmail());
		user.setPassword(userRequest.getPassword());
		user.setUserRole(userRequest.getUserRole());
		
		return user;
		
	}
	
	public UserResponse mapToUserResponse(User user) {
		
		
		UserResponse userResponse= new UserResponse();
		userResponse.setId(user.getId());
		userResponse.setName(user.getName());
		userResponse.setEmail(user.getEmail());
		userResponse.setUserRole(user.getUserRole());
		return userResponse;
		
	}
	
	
	
}
