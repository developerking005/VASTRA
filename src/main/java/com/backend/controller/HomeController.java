package com.backend.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.controller.model.User;
import com.backend.service.UserService;


@RestController
@RequestMapping("/home")
public class HomeController {
	
	
//	http://localhost:8080/home/users
		
	
	private UserService userSerivce;
	
	public HomeController(UserService userSerivce) {
		this.userSerivce=userSerivce;
		// TODO Auto-generated constructor stub
	}
	
	
//	so is user ko access krne ka id password properties files m h or ye spring secuirty lagne ke bad aya h 
	
//	apne pass ek or tarika h user name or password define krne ka jisme apan ek java class banyenge or usme 3 bean define krenge, See AppConfig
	@GetMapping("/users")
	public List<User> getUser()
	{
		
		
		System.out.println("Getting users");
		return this.userSerivce.getUsers();
	}
	
	@GetMapping("/current-user")
	public String getLoggedInUser(Principal principal) {
		return principal.getName();
	}
	
	

	
}
