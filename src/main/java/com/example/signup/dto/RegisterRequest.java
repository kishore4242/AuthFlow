package com.example.signup.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterRequest {
    @NotBlank(message = "Username is not null")
    @Size(min = 4,max = 20, message = "Username is between 4 to 20 char")
    private String userName;

    @NotBlank(message = "Password must be strong")
    @Size(min=8, max = 20, message = "Password must be 8 to 20 character")
    private String password;


}

