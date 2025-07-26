package com.myoidc.auth_server.controllers;

import com.myoidc.auth_server.dto.QuestionDTO;
import com.myoidc.auth_server.dto.UserEntityDTO;
import com.myoidc.auth_server.dto.UserRegistrationDTO;
import com.myoidc.auth_server.models.Question;
import com.myoidc.auth_server.models.UserEntity;
import com.myoidc.auth_server.services.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/users")
@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserEntityDTO> createUser(@RequestBody UserRegistrationDTO dto) {
        try {
            UserEntityDTO user = userService.save(dto);
            return ResponseEntity.ok(user);
        } catch (Exception ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    public ResponseEntity<Page<UserEntityDTO>> getUsers(
            @RequestParam(defaultValue = "") String query,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<UserEntity> users = userService.getUsers(pageable, query);
        Page<UserEntityDTO> dtoPage = users.map(UserEntity::toDTO);
        return ResponseEntity.ok(dtoPage);
    }

    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UserEntityDTO> authenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        UserEntity currentUser = (UserEntity) authentication.getPrincipal();

        return ResponseEntity.ok(currentUser.toDTO());
    }


}
