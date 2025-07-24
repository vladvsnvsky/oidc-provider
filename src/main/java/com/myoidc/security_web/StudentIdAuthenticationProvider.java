package com.myoidc.security_web;

import com.myoidc.auth_server.services.MyDatabaseUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class StudentIdAuthenticationProvider implements AuthenticationProvider {

    private final MyDatabaseUserDetailsService userDetailsService;

    public StudentIdAuthenticationProvider(MyDatabaseUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String studentId = authentication.getName();
        UserDetails userDetails = userDetailsService.loadUserByUsername(studentId);

        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }


}
