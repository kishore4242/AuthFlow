package com.example.signup.service.impl;

import com.example.signup.service.HomeService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
public class HomeServiceImpl implements HomeService {

    @PreAuthorize("hasAnyRole('ROLE_USER')")
    @Override
    public ResponseEntity<?> homepage() {
        return ResponseEntity.ok("Inside the home page");
    }
}
