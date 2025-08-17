package com.example.signup.dto;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter @Setter
public class PasswordResetRequest {
    private String email;

    private String username;

    public PasswordResetRequest(String email,String username) {
        this.email = email;
        this.username = username;
    }
    public PasswordResetRequest(){

    }
}
