package com.codemobiles.stock_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codemobiles.stock_backend.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
	// select * from user where username = 'fool'
	User findByUsername(String username);
}
