package com.myoidc.auth_server.services;

import com.myoidc.auth_server.dto.UserEntityDTO;
import com.myoidc.auth_server.dto.UserRegistrationDTO;
import com.myoidc.auth_server.models.Role;
import com.myoidc.auth_server.models.RoleEnum;
import com.myoidc.auth_server.models.UserEntity;
import com.myoidc.auth_server.repositories.RoleRepository;
import com.myoidc.auth_server.repositories.UserEntityRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    private final UserEntityRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserEntityRepository userRepository,
                       RoleRepository roleRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserEntityDTO save(UserRegistrationDTO dto) {
        Role role = roleRepository.findByName(RoleEnum.USER)
                .orElseThrow(() -> new RuntimeException("Role not found"));

        UserEntity user = new UserEntity();
        user.setEmail(dto.getEmail());
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setBirthdate(LocalDate.parse(dto.getBirthdate()));
        user.setHashedPassword(passwordEncoder.encode(dto.getPassword()));
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        user.setRole(role);

        return userRepository.save(user).toDTO();
    }

    public UserEntityDTO createAdministrator(UserRegistrationDTO input) {
        Role role = roleRepository.findByName(RoleEnum.ADMIN)
                .orElseThrow(() -> new RuntimeException("Admin role not found"));

        UserEntity user = new UserEntity();
        user.setFirstName(input.getFirstName());
        user.setEmail(input.getEmail());
        user.setLastName(input.getLastName());
        user.setHashedPassword(passwordEncoder.encode(input.getPassword()));
        user.setRole(role);

        return userRepository.save(user).toDTO();
    }

    public Page<UserEntity> getUsers(Pageable pageable, String query) {
        if (query.isBlank()) {
            return userRepository.findAll(pageable);
        }
        return userRepository
                .findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCaseOrEmailContainingIgnoreCase(
                        query, query, query, pageable);
    }

    public Optional<UserEntity> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + email));

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getHashedPassword(),
                List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().getName().name()))
        );
    }


}

