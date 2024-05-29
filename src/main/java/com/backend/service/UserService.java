package com.backend.service;

import java.util.List;

import com.backend.controller.model.User;
import com.backend.exception.UserException;


public interface UserService {
	
	public List<User> getUsers();
	
	public User createUser(User user);
	
	public User findUserById(Long userId)throws UserException;
	
	public User findUserProfileByJwt(String jwt) throws UserException ;

}
