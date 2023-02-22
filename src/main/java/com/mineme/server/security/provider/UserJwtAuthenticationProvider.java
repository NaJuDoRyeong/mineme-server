package com.mineme.server.security.provider;

import com.mineme.server.security.service.CustomUserDetailsService;
import com.mineme.server.security.token.UserJwtAuthenticationToken;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserJwtAuthenticationProvider implements AuthenticationProvider {

	private final CustomUserDetailsService userDetailsService;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String principal = (String)authentication.getPrincipal();
		UserDetails user = userDetailsService.loadUserByUsername(principal);

		log.info("Principal User - {}", user.getUsername());

		return new UserJwtAuthenticationToken(user.getUsername());
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}
}
