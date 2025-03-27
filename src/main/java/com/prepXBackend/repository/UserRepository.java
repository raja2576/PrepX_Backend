package com.prepXBackend.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prepXBackend.model.User;


public interface UserRepository extends JpaRepository<User,Long> {

	User findByEmail(String email);

	
}


