package com.backend.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.controller.model.User;


//public interface UserRepository extends JpaRepository<User, String>{
public interface UserRepository extends JpaRepository<User, Long>{
		
	public User findByEmail(String email);

}
