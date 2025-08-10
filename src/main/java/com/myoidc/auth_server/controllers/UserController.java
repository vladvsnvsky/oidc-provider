package com.myoidc.auth_server.controllers;

import com.myoidc.auth_server.dto.QuestionDTO;
import com.myoidc.auth_server.dto.UserEntityDTO;
import com.myoidc.auth_server.dto.UserRegistrationDTO;
import com.myoidc.auth_server.dto.UserUpdateDTO;
import com.myoidc.auth_server.models.ApiResponse;
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


@RequestMapping("/api/users")
@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse> createUser(@RequestBody UserRegistrationDTO dto) {
        try {
            UserEntityDTO user = userService.save(dto);
            ApiResponse apiResp = new ApiResponse(
                    true,
                    user,
                    "success"
            );
            return ResponseEntity.ok(apiResp);
        } catch (Exception ex) {
            ApiResponse apiResp = new ApiResponse(
                    false,
                    null,
                    "There was an error and we couldn't create a new user."
            );
            return ResponseEntity.ok(apiResp);
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
    public ResponseEntity<ApiResponse> authenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("Authorities: " + authentication.getAuthorities());
        UserEntity currentUser = (UserEntity) authentication.getPrincipal();

        ApiResponse apiResp = new ApiResponse(
                true,
                currentUser.toDTO(),
                "success"
        );
        return ResponseEntity.ok(apiResp);
    }

    @PutMapping(
            value = "/{id}",
            consumes = "application/json",
            produces = "application/json"
    )
    public ResponseEntity<UserEntityDTO> updateUser(
            @PathVariable("id") java.util.UUID id,
            @RequestBody UserUpdateDTO dto) {
        UserEntityDTO updated = userService.update(id, dto);
        return ResponseEntity.ok(updated);
    }


}
