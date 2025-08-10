package com.example.signup.service;

import com.example.signup.dto.LoginDTO;
import com.example.signup.exception.InvalidCredentialException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class LoginService {

    private final StringRedisTemplate redisTemplate;
    private final AuthenticationManager authenticationManager;

    public ResponseEntity<?> login(String username, String password) {
        log.debug("Attempt login for user: {}", username);
        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );

            String sessionToken = UUID.randomUUID().toString();
            String userDetails = username + ":" + auth.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.joining(","));

            log.info("Generated session token: {} for user: {}", sessionToken, username);

            redisTemplate.opsForValue().set("session:" + sessionToken, userDetails, 100, TimeUnit.SECONDS);

            return ResponseEntity.ok(new LoginDTO("Login Success", sessionToken));

        } catch (AuthenticationException e) {
            log.warn("Authentication failed for user: {}", username);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }
    }
}

