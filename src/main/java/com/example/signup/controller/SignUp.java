package com.example.signup.controller;

import com.example.signup.dto.RegisterRequest;
import com.example.signup.service.SignupService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;



@RestController("/")
public class SignUp {

    private final SignupService signupService;

    SignUp(SignupService signupService){
        this.signupService = signupService;
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@Valid @RequestBody RegisterRequest request){
        try {
            return signupService.register(request);
        }
        catch (Exception e){
            return new ResponseEntity<>("Error Occur", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
