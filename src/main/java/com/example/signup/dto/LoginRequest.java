package com.example.signup.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LoginRequest {

    @Email
    @NotBlank(message = "email should be valid")
    @Size(min=10, max = 100, message = "Email should be 10 to 100 char")
    private String email;

    @NotBlank(message = "Password must be strong")
    @Size(min=8, max = 20, message = "Password must be 8 to 20 character")
    private String password;
}
