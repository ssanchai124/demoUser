package com.codemobiles.stock_backend.security;

public interface SecurityConstants {
	String SECRET_KEY = "iBlurBlurDevJWS";
	String TOKEN_PREFIX = "Bearer ";
	String HEADER_AUTHORIZATION = "Authorization";
	String CLAIMS_ROLE = "role";
	long EXPIRATION_TIME = (1 * 60 * 1000);
}
