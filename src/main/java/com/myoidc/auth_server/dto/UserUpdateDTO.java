package com.myoidc.auth_server.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.myoidc.auth_server.models.Role;
import com.myoidc.auth_server.models.RoleEnum;
import com.myoidc.auth_server.models.UserEntity;
import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public class UserUpdateDTO {
    private UUID id;
    private String email;
    private String firstName;
    private String lastName;
    private int attempts;
    private LocalDate birthdate;
    private LocalDateTime updatedAt;
    private String hashedPassword;
    private RoleEnum role;

    public UserUpdateDTO(){}

    public UserUpdateDTO(UUID id, UserEntity user){
        this.id = id;
        this.email = user.getEmail();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.attempts = user.getAttempts();
        this.birthdate = user.getBirthdate();
        this.updatedAt = LocalDateTime.now();
        this.hashedPassword = user.getHashedPassword();
        this.role = user.getRole().getName();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAttempts() {
        return attempts;
    }

    public void setAttempts(int attempts) {
        this.attempts = attempts;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    public RoleEnum getRole() {
        return role;
    }

    public void setRole(RoleEnum role) {
        this.role = role;
    }
}
