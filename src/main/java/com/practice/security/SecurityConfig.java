package com.practice.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

	@Autowired
	private UserDetailsService detailsService;
	
	@Autowired
	private JwtService jwtService;
	
	@Bean
	 PasswordEncoder passwordEncoder() {
		 
		 return new BCryptPasswordEncoder();
	 }
	
	@Bean
	 SecurityFilterChain securityFilterChain(HttpSecurity httpsecurity) throws Exception  {
	return	httpsecurity.csrf(csrf->csrf.disable())
		.authorizeHttpRequests(authorize->authorize.requestMatchers("/register","/user-login")
				.permitAll()
				.anyRequest()
				.authenticated())
		.authenticationProvider(authenticationProvider())
		.sessionManagement(session-> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
		.addFilterBefore(new JWTFilter(jwtService), UsernamePasswordAuthenticationFilter.class).build();
		
	}
	
	@Bean
	AuthenticationProvider authenticationProvider() {
		
	DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
	provider.setUserDetailsService(detailsService);
	provider.setPasswordEncoder(passwordEncoder());
	return provider;
		
	}
	
	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
		
		return configuration.getAuthenticationManager();
		
	}
		
}
