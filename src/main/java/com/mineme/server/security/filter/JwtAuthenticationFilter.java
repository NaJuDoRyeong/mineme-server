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

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private final JwtTokenProvider jwtTokenProvider;
	private final Properties properties;

	private static final String EXCEPTION = "exception";

	@Override
	public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws
		IOException, ServletException {

		try {
			String token = jwtTokenProvider.resolve(request);

			if (token == null)
				throw new NullPointerException();

			if (!jwtTokenProvider.validate(token, properties.getSecret()))
				throw new CustomJwtException(ErrorCode.INVALID_TOKEN);

			Authentication authentication = jwtTokenProvider.getAuthentication(token, properties.getSecret());
			SecurityContextHolder.getContext().setAuthentication(authentication);

		} catch (NullPointerException e) {
			request.setAttribute(EXCEPTION, ErrorCode.NULL_TOKEN.getCode());
		} catch (SecurityException e) {
			request.setAttribute(EXCEPTION, ErrorCode.INVALID_TOKEN_SIGNATURE.getCode());
		} catch (MalformedJwtException e) {
			request.setAttribute(EXCEPTION, ErrorCode.INVALID_TOKEN_FORMAT.getCode());
		} catch (ExpiredJwtException e) {
			request.setAttribute(EXCEPTION, ErrorCode.TOKEN_EXPIRED.getCode());
		} catch (UnsupportedJwtException e) {
			request.setAttribute(EXCEPTION, ErrorCode.UNSUPPORTED_TOKEN.getCode());
		} catch (IllegalArgumentException e) {
			request.setAttribute(EXCEPTION, ErrorCode.EMPTY_TOKEN_CLAIMS.getCode());
		} catch (Exception e) {
			request.setAttribute(EXCEPTION, ErrorCode.INVALID_TOKEN.getCode());
		}

		chain.doFilter(request, response);
	}
}
