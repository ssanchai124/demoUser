package com.codemobiles.stock_backend.security;

import static com.codemobiles.stock_backend.security.SecurityConstants.CLAIMS_ROLE;
import static com.codemobiles.stock_backend.security.SecurityConstants.HEADER_AUTHORIZATION;
import static com.codemobiles.stock_backend.security.SecurityConstants.SECRET_KEY;
import static com.codemobiles.stock_backend.security.SecurityConstants.TOKEN_PREFIX;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

	public JWTAuthorizationFilter(AuthenticationManager authenticationManager) {
		super(authenticationManager);
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		String authorizationHeader = request.getHeader(HEADER_AUTHORIZATION);
		if (authorizationHeader != null && authorizationHeader.startsWith(TOKEN_PREFIX)) {
			UsernamePasswordAuthenticationToken authentication = getAuthentication(authorizationHeader);
			SecurityContextHolder.getContext().setAuthentication(authentication);
		}

		chain.doFilter(request, response);
	}

	private UsernamePasswordAuthenticationToken getAuthentication(String jwt) {

		Claims claims = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(jwt.replace(TOKEN_PREFIX, "")).getBody();

		String username = claims.getSubject();
		if (username == null) {
			return null;
		}

		ArrayList<String> roles = (ArrayList<String>) claims.get(CLAIMS_ROLE);
		ArrayList<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
		if (roles != null) {
			for (String role : roles) {
				grantedAuthorities.add(new SimpleGrantedAuthority(role));
			}
		}

		return new UsernamePasswordAuthenticationToken(username, null, grantedAuthorities);
	}

}
