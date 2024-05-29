package com.backend.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.controller.model.JwtRequest;
import com.backend.controller.model.JwtResponse;
import com.backend.controller.model.User;
import com.backend.exception.UserException;
import com.backend.jwt.JwtHelper;
import com.backend.repo.UserRepository;
import com.backend.service.CartService;
import com.backend.service.UserService;




@RestController
@RequestMapping("/auth")
public class AuthController {

	private UserDetailsService userDetailsService;
	
	private AuthenticationManager manager;
	
	private JwtHelper helper;
			
	private UserService userService;
	
	private CartService cartService;
	
	private UserRepository userRepository;
	
	private PasswordEncoder passwordEncoder;
	
	
	public AuthController(UserDetailsService userDetailsService,AuthenticationManager manager,JwtHelper helper,UserService userService,CartService cartService,UserRepository userRepository,PasswordEncoder passwordEncoder) {
		this.userDetailsService=userDetailsService;
		this.manager=manager;
		this.helper=helper;
		this.userService=userService;
		this.cartService=cartService;
		this.userRepository=userRepository;
		this.passwordEncoder=passwordEncoder;
	}
	
	@PostMapping("/login")
	public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest request){
		System.out.println("fsdfsfsfdsfdsfdsfsfsfsdfsdfsdfdsfdfdfssfdffsfdsfs");
		String ds =request.getEmail();
		if(ds==null)
		{
			System.out.println("fsdfsfs4512458512"
					+ "4");
		}
		else {
			System.out.println("fs");
		}
		this.doAuthenticate(request.getEmail(), request.getPassword());
		
		
		
		UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
		String token = this.helper.generateToken(userDetails);		
		JwtResponse response = JwtResponse.builder()				
				.jwtToken(token)
				.username(userDetails.getUsername())				
				.build();
			
			String usernameFormToken = this.helper.getUsernameFromToken(token);
			System.out.println(usernameFormToken);
		 return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	private void doAuthenticate (String username, String password) {
		
		
		
		UsernamePasswordAuthenticationToken authentication= new UsernamePasswordAuthenticationToken(username, password);
		try {
			System.out.println("Authentication start");
			manager.authenticate(authentication);
		}
		catch(BadCredentialsException e){
			throw new RuntimeException("invalid usename or password ! !");
		}
	}
	
//	
//	@PostMapping("/create")
//	public User createUser(@RequestBody User user) {	
//			user.setPassword(passwordEncoder.encode(user.getPassword()));
//			
//			User createdUser= userRepository.save(user);
//			cartService.creatCart(createdUser);
//	
//		return createdUser;
//	}
	
	@PostMapping("/signup")
	public ResponseEntity<JwtResponse> createUserWithJWT(@RequestBody User user) {	
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			
			User createdUser= userRepository.save(user);
			cartService.creatCart(createdUser);
			

			UserDetails userDetails = userDetailsService.loadUserByUsername(createdUser.getEmail());
			String token = this.helper.generateToken(userDetails);
			if(token.isEmpty()){
				JwtResponse response = JwtResponse.builder()
						.message("Token is empty")
						.build();
				return new ResponseEntity<>(response, HttpStatus.OK);
			}
			else{
				JwtResponse response = JwtResponse.builder()
						.jwtToken(token)
						.username(userDetails.getUsername())
						.message("Sigin Successfully")
						.build();

				String usernameFormToken = this.helper.getUsernameFromToken(token);
				System.out.println(usernameFormToken);
				return new ResponseEntity<>(response, HttpStatus.OK);
			}
	}
}
