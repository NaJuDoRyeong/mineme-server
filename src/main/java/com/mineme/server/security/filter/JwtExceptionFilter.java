package com.mineme.server.security.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.mineme.server.common.enums.ErrorCode;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtExceptionFilter extends OncePerRequestFilter {
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException {
		try {
			filterChain.doFilter(request, response);
		} catch (SecurityException e) {
			request.setAttribute("exception", ErrorCode.INVALID_TOKEN_SIGNATURE.getCode());
		} catch (MalformedJwtException e) {
			request.setAttribute("exception", ErrorCode.INVALID_TOKEN_FORMAT.getCode());
		} catch (ExpiredJwtException e) {
			request.setAttribute("exception", ErrorCode.TOKEN_EXPIRED.getCode());
		} catch (UnsupportedJwtException e) {
			request.setAttribute("exception", ErrorCode.UNSUPPORTED_TOKEN.getCode());
		} catch (IllegalArgumentException e) {
			request.setAttribute("exception", ErrorCode.EMPTY_TOKEN_CLAIMS.getCode());
		} catch (Exception e) {
			request.setAttribute("exception", ErrorCode.INVALID_TOKEN.getCode());
		}

		filterChain.doFilter(request, response);
	}
}
