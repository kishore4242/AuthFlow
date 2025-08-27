package com.example.signup.dto;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter @Setter
public class PasswordResetRequest {
    private String email;

    private String username;

    private String otp;

    public PasswordResetRequest(String email,String username,String otp) {
        this.email = email;
        this.username = username;
        this.otp = otp;
    }
    public PasswordResetRequest(){

    }
}
