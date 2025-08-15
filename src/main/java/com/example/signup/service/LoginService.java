package com.example.signup.service;

import com.example.signup.security.auth.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class LoginService {

    private final StringRedisTemplate redisTemplate;
    private final AuthenticationManager authenticationManager;
    private final MyUserDetailsService myUserDetailsService;
    private final JwtService jwtService;

    public ResponseEntity<?> login(String username, String password) {
        log.debug("Attempt login for user: {}", username);
        String jwtToken = null;
        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );

            String userDetails = username + ":" + auth.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.joining(","));
            if(auth.isAuthenticated()){
                UserDetails userDetails1 = myUserDetailsService.loadUserByUsername(username);

                jwtToken = jwtService.generateToken(userDetails1);
                return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION,"Bearer "+jwtToken).body("Jwt created Done");

            }


            log.info(jwtToken);
            return ResponseEntity.ok("user not authenticated");

        } catch (AuthenticationException e) {
            log.warn("Authentication failed for user: {}", username);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }
    }
}

