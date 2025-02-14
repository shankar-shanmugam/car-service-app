package com.practice.security;

import java.io.IOException;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JWTFilter extends OncePerRequestFilter {

	private JwtService jwtService;

	public JWTFilter(JwtService jwtService) {

		this.jwtService = jwtService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String token = request.getHeader("Authorization");
		if (token != null) {
			
			token = token.substring(7);
			Claims claims = jwtService.parseJWT(token);
			String userRole = claims.get("role", String.class);
			String username = claims.get("user", String.class);

			if (username != null && userRole != null
					&& SecurityContextHolder.getContext().getAuthentication() == null) {

				UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
						username, null, List.of(new SimpleGrantedAuthority(userRole)));
				authenticationToken.setDetails(new WebAuthenticationDetails(request));

				SecurityContextHolder.getContext().setAuthentication(authenticationToken);

			}

		}

		filterChain.doFilter(request, response);
	}

}
