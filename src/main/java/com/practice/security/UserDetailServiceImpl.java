package com.practice.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.practice.repository.UserRepository;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

	@Autowired
	private UserRepository userRepository; 
	
	@Override
	public UserDetails loadUserByUsername(String username)  {

//		Optional<User> byEmail = userRepository.findByEmail(username);
//		User user = byEmail.get();
//		UserDetails userDetail= new UserDetailImpl(user);
//		
//		return userDetail;
//		
		return userRepository.findByEmail(username)
				.map(user-> new UserDetailImpl(user))
				.orElseThrow(()->new UsernameNotFoundException("email not found"));
		
	}

	
}
