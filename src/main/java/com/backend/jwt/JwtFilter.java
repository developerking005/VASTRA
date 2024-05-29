package com.backend.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class JwtFilter {
	
	@Autowired
	private JwtHelper jwtHelper;
	
	private Logger logger = LoggerFactory.getLogger(OncePerRequestFilter.class);

	public String getTokenWithoutBearer(String jwt) {

		logger.info(" Header :  {}", jwt);
		String username = null;
		String token = null;
		if (jwt != null && jwt.startsWith("Bearer")) {
			// looking good
			token = jwt.substring(7);
			
			return token;

		} else {
			logger.info("Invalid Header Value !! ");
			return "token is invalid";
					
		}
		
	}
}
