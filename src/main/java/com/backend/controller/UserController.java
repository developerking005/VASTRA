package com.backend.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.controller.model.User;
import com.backend.exception.UserException;
import com.backend.service.UserService;


@RestController
@RequestMapping("/api/users")
public class UserController {
	
	private UserService userService;
	
	public UserController(UserService userService) {
		this.userService=userService;		
	}
	
	@GetMapping("/profile")
	public ResponseEntity<User> getUserProfileHandler(@RequestHeader("Authorization")String jwt)throws UserException{
		
		System.out.println("Getting logged in user");
		System.out.println("fsdfds");
		User user = userService.findUserProfileByJwt(jwt);
		System.out.println(user);
		return new ResponseEntity<User>(user,HttpStatus.ACCEPTED);
	}
}
