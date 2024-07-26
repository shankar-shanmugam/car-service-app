package com.practice.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

	@Autowired
	private UserDetailsService detailsService;
	
	@Bean
	 PasswordEncoder passwordEncoder() {
		 
		 return new BCryptPasswordEncoder();
	 }
	
	@Bean
	 SecurityFilterChain securityFilterChain(HttpSecurity httpsecurity) throws Exception  {
	return	httpsecurity.csrf(csrf->csrf.disable())
		.authorizeHttpRequests(authorize->authorize.requestMatchers("/register")
				.permitAll()
				.anyRequest()
				.authenticated()).authenticationProvider(authenticationProvider()).formLogin(Customizer.withDefaults()).build();
		
	}
	
	@Bean
	AuthenticationProvider authenticationProvider() {
		
	DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
	provider.setUserDetailsService(detailsService);
	provider.setPasswordEncoder(passwordEncoder());
	return provider;
		
	}
	
}
