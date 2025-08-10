package com.example.signup.dto;

import lombok.*;
import org.springframework.stereotype.Service;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginDTO {
    private String message;
    private String token;
}
