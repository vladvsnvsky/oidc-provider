package com.myoidc.auth_server.services;

import com.myoidc.auth_server.dto.UserEntityDTO;
import com.myoidc.auth_server.dto.UserRegistrationDTO;
import com.myoidc.auth_server.dto.UserUpdateDTO;
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
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

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

    @Transactional
    public UserEntityDTO update(UUID dbId, UserUpdateDTO dto) {
        UserEntity fromDb = userRepository.findById(dbId).orElseThrow(()->
           new RuntimeException("User not found!")
        );

        if (dto.getId() != null && !dto.getId().equals(dbId)) {
            throw new RuntimeException("Cannot update the id");
        }

        if (dto.getEmail() != null && !Objects.equals(dto.getEmail(), fromDb.getEmail())) {
            if (userRepository.findByEmail(dto.getEmail()).isPresent()) {
                throw new IllegalArgumentException("Email already in use");
            }
            fromDb.setEmail(dto.getEmail());
            System.out.println("Email changed...");
        }

        if (dto.getFirstName() != null && !Objects.equals(dto.getFirstName(), fromDb.getFirstName())) {
            fromDb.setFirstName(dto.getFirstName());
            System.out.println("First Name changed! ...");
        }

        if (dto.getLastName() != null && !Objects.equals(dto.getLastName(), fromDb.getLastName())) {
            fromDb.setLastName(dto.getLastName());
            System.out.println("Last Name changed! ...");
        }

        if (dto.getBirthdate() != null && !Objects.equals(dto.getBirthdate(), fromDb.getBirthdate())) {
            fromDb.setBirthdate(dto.getBirthdate());
            System.out.println("Birthdate changed! ...");
        }

        if (dto.getAttempts() != fromDb.getAttempts()) {
            fromDb.setAttempts(dto.getAttempts());
            System.out.println("Attempts changed! ...");
        }

        if (dto.getHashedPassword() != null && !Objects.equals(dto.getHashedPassword(), fromDb.getHashedPassword())) {
            fromDb.setHashedPassword(dto.getHashedPassword());
            System.out.println("Password changed! ...");
        }

        if (dto.getRole() != null
                && (fromDb.getRole() == null || !Objects.equals(fromDb.getRole().getName(), dto.getRole()))) {
            Role role = roleRepository.findByName(dto.getRole())
                    .orElseThrow(() -> new IllegalArgumentException("Role not found"));
            fromDb.setRole(role);
        }

        return userRepository.save(fromDb).toDTO();
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

