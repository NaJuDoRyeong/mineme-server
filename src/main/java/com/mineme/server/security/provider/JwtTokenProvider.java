package com.mineme.server.security.provider;

import com.mineme.server.common.enums.ErrorCode;
import com.mineme.server.common.exception.CustomException;
import com.mineme.server.entity.User;
import com.mineme.server.security.service.CustomUserDetailsService;
import com.mineme.server.security.token.UserJwtAuthenticationToken;

import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

import java.security.PublicKey;
import java.util.Date;
import java.util.Optional;

/**
 * @Todo 애플 인가 작업 후 일괄 예외처리 핸들러로 넘길 예정
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class JwtTokenProvider {

	private static final long EXPIREDTIME = 3 * 24 * 60 * 60 * 1000L;

	private final CustomUserDetailsService userDetailsService;

	public String create(String username, String key) {
		Date now = new Date();

		return Jwts.builder()
			.setSubject(username)
			.setIssuedAt(now)
			.setExpiration(new Date(now.getTime() + EXPIREDTIME))
			.signWith(SignatureAlgorithm.HS256, key)
			.compact();
	}

	public Claims getClaims(String token, String key) {
		try {
			return Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
		} catch (SecurityException e) {
			log.info("Invalid JWT signature", e);
		} catch (MalformedJwtException e) {
			log.info("Invalid JWT token", e);
		} catch (ExpiredJwtException e) {
			log.info("Expired JWT Token", e);
		} catch (UnsupportedJwtException e) {
			log.info("Unsupported JWT Token", e);
		} catch (IllegalArgumentException e) {
			log.info("JWT claims string is empty", e);
		} catch (Exception e) {
			log.info("Error occur on JWT", e);
		}

		return null;
	}

	public Claims getClaims(String token, PublicKey key) {
		try {
			return Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
		} catch (SecurityException e) {
			log.info("Invalid JWT signature", e);
		} catch (MalformedJwtException e) {
			log.info("Invalid JWT token", e);
		} catch (ExpiredJwtException e) {
			log.info("Expired JWT Token", e);
		} catch (UnsupportedJwtException e) {
			log.info("Unsupported JWT Token", e);
		} catch (IllegalArgumentException e) {
			log.info("JWT claims string is empty", e);
		} catch (Exception e) {
			log.info("Error occur on JWT", e);
		}

		return null;
	}

	public Authentication getAuthentication(String token, String key) {
		String username = this.getClaims(token, key).getSubject();
		UserDetails userDetails = userDetailsService.loadUserByUsername(username);

		return new UserJwtAuthenticationToken(userDetails);
	}

	public Optional<String> getUsername() {
		final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication == null) {
			return Optional.empty();
		}

		return Optional.ofNullable(((User)authentication.getPrincipal()).getUsername());
	}

	public boolean validate(String token, String key) {
		try {
			Jwts.parser().setSigningKey(key).parseClaimsJws(token);
			return true;
		} catch (SecurityException e) {
			log.info("Invalid JWT signature", e);
		} catch (MalformedJwtException e) {
			log.info("Invalid JWT token", e);
		} catch (ExpiredJwtException e) {
			log.info("Expired JWT Token", e);
		} catch (UnsupportedJwtException e) {
			log.info("Unsupported JWT Token", e);
		} catch (IllegalArgumentException e) {
			log.info("JWT claims string is empty", e);
		} catch (Exception e) {
			log.info("Error occur on JWT", e);
		}

		return false;
	}

	public boolean validate(String token, PublicKey key) {
		try {
			Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
			return true;
		} catch (SecurityException e) {
			log.info("Invalid JWT signature", e);
		} catch (MalformedJwtException e) {
			log.info("Invalid JWT token", e);
		} catch (ExpiredJwtException e) {
			log.info("Expired JWT Token", e);
		} catch (UnsupportedJwtException e) {
			log.info("Unsupported JWT Token", e);
		} catch (IllegalArgumentException e) {
			log.info("JWT claims string is empty", e);
		} catch (Exception e) {
			log.info("Error occur on JWT", e);
		}

		return false;
	}

	public JwsHeader getJwtHeader(String token) {
		return null;
	}

	public String resolve(HttpServletRequest request) {

		String authorization = request.getHeader("Authorization");

		if (authorization == null)
			return null;

		if (authorization.startsWith("Bearer "))
			return authorization.substring(7);

		if (authorization.startsWith("token "))
			return authorization.substring(6);

		throw new CustomException(ErrorCode.INVALID_TOKEN);
	}
}
