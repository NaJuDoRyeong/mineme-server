package com.mineme.server.security.handler;


import com.mineme.server.entity.User;
import com.mineme.server.security.service.CustomUserDetailsService;
import com.mineme.server.security.token.UserJwtAuthenticationToken;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class UserJwtAuthenticationProvider implements AuthenticationProvider {

    private final CustomUserDetailsService userDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        User principal = (User) authentication.getPrincipal();
        UserDetails user = userDetailsService.loadUserByUsername(principal.getUsername());

        return new UserJwtAuthenticationToken(user);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
