package com.example.signup.controller;


import com.example.signup.service.impl.HomeServiceImpl;
import jakarta.validation.constraints.Email;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class HomeController {

    private final HomeServiceImpl homeService;

    @GetMapping("/home")
    public ResponseEntity<?> homepage(){
        return homeService.homepage();
    }

    @PostMapping("/home")
    public ResponseEntity<?> emailInput(@Email @RequestParam Email email){
return null;
    }
}
