package com.practice.serviceImpl;

import java.time.Duration;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.practice.dtorequest.AuthRequest;
import com.practice.dtorequest.UserRequest;
import com.practice.dtoresponse.UserResponse;
import com.practice.entity.Cart;
import com.practice.entity.User;
import com.practice.enums.UserRole;
import com.practice.exception.UserNotFoundByIdException;
import com.practice.mapper.UserMapper;
import com.practice.repository.CartRepository;
import com.practice.repository.UserRepository;
import com.practice.security.JwtService;
import com.practice.service.UserServiceInterface;
import com.practice.utility.ResponseStructure;

@Service
public class UserServiceImpl implements UserServiceInterface {

	private final UserRepository userRepository;
	private final UserMapper userMapper;
	private final CartRepository cartRepository;
	private final AuthenticationManager authenticationManager;
	private final JwtService jwtService;
	

	public UserServiceImpl(UserRepository userRepository, UserMapper userMapper,CartRepository cartRepository,AuthenticationManager authenticationManager,JwtService service) {
		this.userRepository = userRepository;
		this.userMapper = userMapper;
		this.cartRepository=cartRepository;
		this.authenticationManager =authenticationManager;
		this.jwtService=service;
	}

	@Override
	public ResponseEntity<ResponseStructure<UserResponse>> registerUser(UserRequest userRequest) {
					
		User user = userMapper.mapToUser(userRequest);
		user=userRepository.save(user);
		
		if(user.getUserRole().equals(UserRole.CUSTOMER)) {
			
		Cart cart= new Cart();	
		cart.setUser(user);	
		cartRepository.save(cart);
		}
	
		return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseStructure<UserResponse>()
				.setStatusCode(HttpStatus.CREATED.value())
				.setMessage("user object created successfully")
				.setData(userMapper.mapToUserResponse(user)));
	
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

	@Override
	public ResponseEntity<ResponseStructure<String>> login(AuthRequest authRequest) {
		
		String username = authRequest.getUsername();
		String password = authRequest.getPassword();

		UsernamePasswordAuthenticationToken token=new UsernamePasswordAuthenticationToken(username,password,null);
		Authentication authenticate = authenticationManager.authenticate(token);
		if(authenticate.isAuthenticated()) {
			SecurityContextHolder.getContext().setAuthentication(authenticate);
			
			return userRepository.findByEmail(username)
					.map(user->
					{
					
					String jwt=	jwtService.createJWT(username, user.getUserRole().name(), Duration.ofDays(1));
					
					return ResponseEntity.status(HttpStatus.OK).body(new ResponseStructure<String>()
							.setStatusCode(HttpStatus.OK.value()).setMessage("successfully logged").setData(jwt));
					
		})
		.orElseThrow(()->new UsernameNotFoundException("user credentials is not found"));
		
	}
		else {
			throw new UsernameNotFoundException("user credentials not found");
		}
	}
}
