package com.mineme.server.security.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.mineme.server.common.enums.ErrorCode;
import com.mineme.server.common.exception.CustomJwtException;
import com.mineme.server.security.config.Properties;
import com.mineme.server.security.provider.JwtTokenProvider;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private final JwtTokenProvider jwtTokenProvider;
	private final Properties properties;

	@Override
	public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws
		IOException,
		ServletException {

		String token = jwtTokenProvider.resolve(request);

		if (token == null)
			throw new CustomJwtException(ErrorCode.NULL_TOKEN);

		if (!jwtTokenProvider.validate(token, properties.getSecret()))
			throw new CustomJwtException(ErrorCode.INVALID_TOKEN);

		Authentication authentication = jwtTokenProvider.getAuthentication(token, properties.getSecret());
		SecurityContextHolder.getContext().setAuthentication(authentication);

		chain.doFilter(request, response);
	}
}
