package com.mineme.server.security.config;

import lombok.RequiredArgsConstructor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.mineme.server.security.filter.JwtAuthenticationFilter;
import com.mineme.server.security.filter.JwtGlobalEntryPoint;
import com.mineme.server.security.provider.JwtTokenProvider;
import com.mineme.server.security.provider.UserJwtAuthenticationProvider;
import com.mineme.server.security.service.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private final CustomUserDetailsService userDetailsService;
	private final JwtGlobalEntryPoint jwtGlobalEntryPoint;
	private final JwtTokenProvider jwtTokenProvider;
	private final Properties properties;

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean
	public AuthenticationProvider authenticationProvider() {
		return new UserJwtAuthenticationProvider(userDetailsService);
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();

		http.httpBasic()
			.disable()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
			.authorizeRequests()
			.antMatchers("/api/v1/auth/**").permitAll()
			.and()
			.authorizeRequests()
			.anyRequest().authenticated()
			.and()
			.exceptionHandling().authenticationEntryPoint(jwtGlobalEntryPoint)
			.and()
			.addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider, properties),
				UsernamePasswordAuthenticationFilter.class);
	}
}
