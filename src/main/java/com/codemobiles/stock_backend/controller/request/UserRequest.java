package com.codemobiles.stock_backend.controller.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Length;
import org.springframework.stereotype.Service;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Service
@NoArgsConstructor
public class UserRequest {

	@NotEmpty
	@Size(min = 1, max = 100)
	private String username;

	@NotEmpty
	@Length(min = 8, message = "The field must be at least {min} characters")
	private String password;

	@NotEmpty
	private String role;
}
