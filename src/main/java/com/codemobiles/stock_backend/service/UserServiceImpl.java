package com.codemobiles.stock_backend.service;

import java.util.Optional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.codemobiles.stock_backend.controller.request.UserRequest;
import com.codemobiles.stock_backend.exception.UserDuplicateException;
import com.codemobiles.stock_backend.model.User;
import com.codemobiles.stock_backend.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.userRepository = userRepository;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}

	@Override
	public User register(UserRequest userRequest) {
		User user = userRepository.findByUsername(userRequest.getUsername());
		if (user == null) {
			user = new User().setUsername(userRequest.getUsername())
					.setPassword(bCryptPasswordEncoder.encode(userRequest.getPassword()))
					.setRole(userRequest.getRole());

			return userRepository.save(user);
		}
		throw new UserDuplicateException(userRequest.getUsername());
	}

	@Override
	public User findUserByUsername(String username) {
		Optional<User> user = Optional.ofNullable(userRepository.findByUsername(username));
		if (user.isPresent()) {
			return user.get();
		}
		return null;
	}

}
