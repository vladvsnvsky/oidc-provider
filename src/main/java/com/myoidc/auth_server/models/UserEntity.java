package com.myoidc.auth_server.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.myoidc.auth_server.dto.UserEntityDTO;
import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class UserEntity {
    @Id
    @UuidGenerator
    private UUID id;

    @Column(name = "EMAIL", nullable = false, unique = true)
    private String email;
    private String firstName;
    private String lastName;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthdate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime updatedAt;
    private String hashedPassword;

    // Getters and setters
    public UserEntity(){

    }

    public UserEntity(String email){
        this.email = email;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = createdAt;
    }

    public static UserEntity fromDTO(UserEntityDTO dto) {
        UserEntity result = new UserEntity();
        result.setEmail(dto.getEmail());
        result.setFirstName(dto.getFirstName());
        result.setLastName(dto.getLastName());
        result.setBirthdate(dto.getBirthdate());
        result.setCreatedAt(dto.getCreatedAt());
        result.setUpdatedAt(dto.getUpdatedAt());
        return result;
    }

    public UserEntityDTO toDTO() {
        return new UserEntityDTO(
                this.getId(),
                this.getEmail(),
                this.getFirstName(),
                this.getLastName(),
                this.getBirthdate(),
                this.getCreatedAt(),
                this.getUpdatedAt()
        );
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

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
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
}
