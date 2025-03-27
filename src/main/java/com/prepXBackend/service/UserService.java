package com.prepXBackend.service;

import java.util.Optional;

import com.prepXBackend.model.User;

public interface UserService {
	
	 User findUserProfileByJwt(String jwt) throws Exception;
	 
	 Optional<User> getUserByEmail(String email) throws Exception;
	 
	 User findUserById(Long userId)throws Exception;
	 
	 public User getUserFromToken(String token) throws Exception;
	 

}
