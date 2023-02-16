package com.mineme.server.security.token;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class UserJwtAuthenticationToken extends AbstractAuthenticationToken {

	private final Object principal;

	public UserJwtAuthenticationToken(Object principal) {
		super(null);
		super.setAuthenticated(false);
		this.principal = principal;
	}

	public UserJwtAuthenticationToken(Collection<? extends GrantedAuthority> authorities, Object principal) {
		super(authorities);
		super.setAuthenticated(true);
		this.principal = principal;
	}

	@Override
	public Object getCredentials() {
		return null;
	}

	@Override
	public Object getPrincipal() {
		return this.principal;
	}

}
