package com.backend.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.backend.controller.model.User;
import com.backend.repo.UserRepository;



@Service
public class CustomerUserDetailService implements UserDetailsService {

	
	private UserRepository userRepository;
	
	
	public CustomerUserDetailService(UserRepository userRepository) {

		this.userRepository=userRepository;
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

//		Load user from database;		
		if(username.isEmpty())
		{
			System.out.println("yes its empty");
			return null;
		}
		else
		{
			System.out.println(username);
			System.out.println("no its not empty");

			User user=userRepository.findByEmail(username);
			System.out.println(user);
			return user;

		}
	}

}
