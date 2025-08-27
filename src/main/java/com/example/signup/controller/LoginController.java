package com.example.signup.controller;

import com.example.signup.dto.EmailDto;
import com.example.signup.dto.LoginRequest;
import com.example.signup.dto.NewPassword;
import com.example.signup.service.LoginService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class LoginController {
    private final LoginService loginService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request){
        try{
            return loginService.login(request.getEmail(), request.getPassword());
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("User not login");
        }
    }
    @PostMapping("/forget-password")
    public ResponseEntity<?> forgetPassword(@RequestBody EmailDto email){
        return loginService.forgetPassword(email);
    }

    @PostMapping("/reset-password/{otp}")
    public ResponseEntity<?> resetPassword(@PathVariable String otp, @RequestBody NewPassword newPassword){
        try{
            return loginService.validateOtpAndSaveNewPassword(otp,newPassword);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.GATEWAY_TIMEOUT);
        }
    }
}
