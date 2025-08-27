package com.example.signup.service;

import com.example.signup.service.impl.HomeServiceImpl;
import org.springframework.http.ResponseEntity;

public interface HomeService {
    ResponseEntity<?> homepage();
}
