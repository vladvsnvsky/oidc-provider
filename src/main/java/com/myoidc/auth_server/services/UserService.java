package com.myoidc.auth_server.services;

import com.myoidc.auth_server.dto.UserEntityDTO;
import com.myoidc.auth_server.dto.UserRegistrationDTO;
import com.myoidc.auth_server.models.Question;
import com.myoidc.auth_server.models.UserEntity;
import com.myoidc.auth_server.repositories.UserEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class UserService {
    @Autowired
    private UserEntityRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public UserService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public UserEntityDTO save(UserRegistrationDTO dto) throws Exception{

        UserEntity user = new UserEntity();
        user.setEmail(dto.getEmail());
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setBirthdate(LocalDate.parse(dto.getBirthdate()));
        user.setHashedPassword(passwordEncoder.encode(dto.getPassword()));
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        UserEntity saved = userRepository.save(user);
        return saved.toDTO();
    }

    public Page<UserEntity> getUsers(Pageable pageable, String query) {
        Page<UserEntity> result = null;
        if(query.equals("")) {
             result = userRepository.findAll(pageable);
        }
        else
            result = userRepository
                    .findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCaseOrEmailContainingIgnoreCase(
                            query, query, query, pageable);

        return result;
    }
}
