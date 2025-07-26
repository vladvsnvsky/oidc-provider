package com.myoidc.auth_server.controllers;

import com.myoidc.auth_server.dto.AuthRequestDTO;
import com.myoidc.auth_server.models.AuthResponse;
import com.myoidc.auth_server.services.AuthenticationService;
import com.myoidc.auth_server.services.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/auth")
@RestController
public class AuthController {

    private final AuthenticationService authService;
    private final JwtService jwtService;

    public AuthController(AuthenticationService authService, JwtService jwtService) {
        this.authService = authService;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> authenticate(@RequestBody AuthRequestDTO dto) {
        AuthResponse authResponse = authService.authenticate(dto);

        if(authResponse.isSuccess()){
            String jwtToken = jwtService.generateToken(authResponse.getUser());
            authResponse.setToken(jwtToken);
            authResponse.setExpiresIn(jwtService.getExpirationTime());
        }


        return ResponseEntity.ok(authResponse);
    }
}
