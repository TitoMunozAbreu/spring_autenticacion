package com.example.models;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {

    @NotBlank(message = "Include first name")
    private String firstName;
    @NotBlank(message = "Include last name")
    private String lastName;

    @NotBlank(message = "Must include an email")
    @Email
    @Pattern(regexp = ".*@mail\\.com", message = "Must include a valid domain = example@mail.com")
    @Column(unique = true)
    private String email;

    @NotBlank(message = "Must include a password")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[!@#$%^&()-+]).{8}$", message = "At least 8 characters in length, Include at least one uppercase letter. Include at least one symbol (!@#$%^&*()_+). ")
    private String password;
    private Role role;

    public UserEntity toUserEnity() {
        return new UserEntity(firstName,lastName,email,password, role);
    }
}
