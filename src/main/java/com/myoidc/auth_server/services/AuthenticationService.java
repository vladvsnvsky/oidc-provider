package com.myoidc.auth_server.services;

import com.myoidc.auth_server.dto.AuthRequestDTO;
import com.myoidc.auth_server.models.AuthResponse;
import com.myoidc.auth_server.models.UserEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final UserService userService;

    public AuthenticationService(AuthenticationManager authenticationManager, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
    }

    public AuthResponse authenticate(AuthRequestDTO request){
        AuthResponse response = new AuthResponse();
        try{
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(request.getEmail(),
                    request.getPassword());
            authenticationManager.authenticate(
                    token
            );

            Optional<UserEntity> inDb = userService.findByEmail(request.getEmail());

            if(inDb.isEmpty())
            {
                throw new RuntimeException("User not found!");
            }

            response.setSuccess(true);
            response.setMessage("Authenticated!");
            response.setUser(inDb.get());
            response.getUser().setHashedPassword(null);
            return response;


        }catch (Exception ex){
            response.setSuccess(false);
            response.setMessage(ex.getMessage());
            return response;
        }



    }
}