package com.myoidc.auth_server.controllers;

import com.myoidc.auth_server.dto.UserEntityDTO;
import com.myoidc.auth_server.dto.UserRegistrationDTO;
import com.myoidc.auth_server.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/admin")
@RestController
@PreAuthorize("hasRole('SUPER_ADMIN')")
public class AdminController {
    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserEntityDTO> createAdministrator(@RequestBody UserRegistrationDTO registerUserDto) {
        UserEntityDTO createdAdmin = userService.createAdministrator(registerUserDto);

        return ResponseEntity.ok(createdAdmin);
    }
}

